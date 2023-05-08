package com.backend.townbottari.service;

import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.exception.SocialLoginException;
import com.backend.townbottari.jwt.JwtTokenProvider;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@PropertySource("classpath:env.properties")
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${auth.kakao.key}")
    private String authKakaoKey;


    public String getKakaoAccessTokenByCode(String code, String authKakaoRedirectUrl) {
        String accessToken = "";
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", authKakaoKey);
            params.add("redirect_uri", authKakaoRedirectUrl);
            params.add("code", code);

            RestTemplate rt = new RestTemplate();
            rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                    new HttpEntity<>(params, headers);

            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(response.getBody());
            accessToken = (String) elem.get("access_token");
        } catch (Exception e) {
            throw new SocialLoginException();
        }
        return accessToken;
    }

    public Optional<User> saveUserInfoByKakaoToken(String accessToken) {
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
            RestTemplate rt = new RestTemplate();
            rt.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

            ResponseEntity<String> response = rt.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest,
                    String.class
            );

            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(response.getBody());

            String id = String.valueOf((Long) elem.get("id"));
            String nickname = (String) ((JSONObject) elem.get("properties")).get("nickname");

            return Optional.ofNullable(User.createKakaoUser(id, nickname));
        } catch (Exception e) {
            throw new SocialLoginException();
        }
    }


    public LoginResponseDto tokenRefresh(String refreshToken){
        boolean isValid = jwtTokenProvider.validateToken(refreshToken) == null;

        if (refreshToken == null || !isValid) {
            throw new BusinessException(ExceptionCode.FAIL_AUTHENTICATION);
        }

        Long userId = jwtTokenProvider.getJwtTokenPayload(refreshToken);
        String usersRefreshToken = userRepository.findRefreshTokenById(userId);

        if (!refreshToken.equals(usersRefreshToken)) {
            throw new BusinessException(ExceptionCode.FAIL_AUTHENTICATION);
        }

        String newRefreshToken = jwtTokenProvider.createRefreshToken(userId);
        updateRefreshToken(userId, newRefreshToken);

        return LoginResponseDto.from(
                jwtTokenProvider.createAccessToken(userId),
                newRefreshToken);
    }

    public void updateRefreshToken(Long userId, String refreshToken) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }
}
