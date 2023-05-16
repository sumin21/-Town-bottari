package com.backend.townbottari.controller;

import com.backend.townbottari.domain.form.dto.FormListResponseDto;
import com.backend.townbottari.domain.form.dto.FormResponseDto;
import com.backend.townbottari.domain.post.dto.PostListResponseDto;
import com.backend.townbottari.domain.user.dto.KakaoLoginRequestDto;
import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.UpdateUserNickNameDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.service.UserService;
import com.backend.townbottari.service.result.MultiplePageResult;
import com.backend.townbottari.service.result.ResponseService;
import com.backend.townbottari.service.result.Result;
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

    @PatchMapping("/profile")
    @ApiOperation(value = "사용자 닉네임 수정 API", response = Result.class)
    public ResponseEntity<Result> updateUserNickName(
            @AuthenticationPrincipal Long userId, @RequestBody @Valid UpdateUserNickNameDto request) {
        userService.updateUserNickName(userId, request);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @PostMapping("/resign")
    @ApiOperation(value = "사용자 탈퇴 API", response = Result.class)
    public ResponseEntity<Result> deleteUser(
            @AuthenticationPrincipal Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(responseService.getSuccessResult());
    }

    @GetMapping("/posts")
    @ApiOperation(value = "사용자 게시글 조회 API", response = UserProfileResponseDto.class)
    public ResponseEntity<MultiplePageResult<PostListResponseDto>> getUsersPosts(@AuthenticationPrincipal Long userId, @PageableDefault(size=10) Pageable pageable) {
        Page<PostListResponseDto> result = userService.getUsersPosts(userId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }

    @GetMapping("/forms")
    @ApiOperation(value = "사용자 신청서 조회 API", response = FormListResponseDto.class)
    public ResponseEntity<MultiplePageResult<FormListResponseDto>> getUsersForms(@AuthenticationPrincipal Long userId, @PageableDefault(size=10) Pageable pageable) {
        Page<FormListResponseDto> result = userService.getUsersForms(userId, pageable);
        return ResponseEntity.ok(responseService.getMultiplePageResult(result));
    }

}
