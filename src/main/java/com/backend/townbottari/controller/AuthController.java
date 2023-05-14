package com.backend.townbottari.controller;

import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.TokenRefreshRequestDto;
import com.backend.townbottari.domain.user.dto.UserProfileResponseDto;
import com.backend.townbottari.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("api/auth")
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/token-refresh")
    @ApiOperation(value = "액세스 토큰 재발급", response = LoginResponseDto.class)
    public LoginResponseDto tokenRefresh(@RequestBody @Valid TokenRefreshRequestDto request) {
        return authService.tokenRefresh(request.getRefreshToken());

    }
}
