package com.backend.townbottari.controller;

import com.backend.townbottari.domain.user.dto.KakaoLoginRequestDto;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.service.UserService;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("api/users")
@RestController
public class UserController {
    private final UserService userService;
//    private final PostService postService;
    private final ResponseService responseService;


    /* 카카오 로그인 API */
    @PostMapping("/kakao-login")
    @ApiOperation(value = "카카오 로그인 API", response = LoginResponseDto.class)
    public LoginResponseDto kakaoLoginUser(@RequestBody @Valid KakaoLoginRequestDto request) {
        return userService.kakaoSignup(request.getCode(), request.getRedirectUrl());
    }

    /* 사용자 프로필 반환 API */
    @GetMapping("/profile")
    @ApiOperation(value = "사용자 프로필 조회 API", response = UserProfileResponseDto.class)
    public ResponseEntity<SingleResult<UserProfileResponseDto>> findUsersProfile(@AuthenticationPrincipal Long userId) {
        UserProfileResponseDto result = userService.findUsersProfile(userId);
        return ResponseEntity.ok(responseService.getSingleResult(result));

    }

}
