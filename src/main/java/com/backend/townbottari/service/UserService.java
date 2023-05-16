package com.backend.townbottari.service;

import com.backend.townbottari.domain.form.Form;
import com.backend.townbottari.domain.form.dto.FormListResponseDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.post.Post;
import com.backend.townbottari.domain.post.dto.PostListResponseDto;
import com.backend.townbottari.domain.user.Role;
import com.backend.townbottari.domain.user.User;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.UpdateUserNickNameDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.exception.BusinessException;
import com.backend.townbottari.exception.ExceptionCode;
import com.backend.townbottari.exception.NotFoundException;
import com.backend.townbottari.jwt.JwtTokenProvider;
import com.backend.townbottari.repository.form.FormRepository;
import com.backend.townbottari.repository.post.PostRepository;
import com.backend.townbottari.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FormRepository formRepository;
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

    public Page<PostListResponseDto> getUsersPosts(Long userId, Pageable page) {
        Page<Post> postPage = postRepository.findByUserId(userId, page);
        return postPage.map(PostListResponseDto::from);
    }

    public Page<FormListResponseDto> getUsersForms(Long userId, Pageable page) {
        Page<Form> formPage = formRepository.findByUserId(userId, page);
        return formPage.map(FormListResponseDto::from);
    }
}
