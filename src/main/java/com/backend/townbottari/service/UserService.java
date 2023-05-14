package com.backend.townbottari.service;

import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.UpdateUserNickNameDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.jwt.JwtTokenProvider;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto kakaoSignup(String code, String redirectUrl) {
        String accessToken = authService.getKakaoAccessTokenByCode(code, redirectUrl);
        Optional<User> kakaoUser = authService.saveUserInfoByKakaoToken(accessToken);
        Optional<User> existUser = userRepository.findByRoleAndSocialId(Role.KAKAO, kakaoUser.get().getSocialId());

        if(!existUser.isEmpty()) return login(existUser.get());

        userRepository.save(kakaoUser.get());
        return login(kakaoUser.get());
    }


    public LoginResponseDto login(User user) {
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
        authService.updateRefreshToken(user.getId(), refreshToken);

        return LoginResponseDto.from(
                jwtTokenProvider.createAccessToken(user.getId()),
                refreshToken);
    }

    public UserProfileResponseDto findUsersProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        return UserProfileResponseDto.from(user);
    }

    public void updateUserNickName(Long userId, UpdateUserNickNameDto request) {
        Optional<User> dupleUser = userRepository.findByNickname(request.getNickname());
        if(dupleUser.isPresent()){
            throw new BusinessException(ExceptionCode.DUPLICATED_USER);
        } else {
            User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
            user.setNickname(request.getNickname());
            userRepository.save(user);
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
