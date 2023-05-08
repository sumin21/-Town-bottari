package com.backend.townbottari.controller;

import com.backend.townbottari.domain.user.dto.LoginResponseDto;
import com.backend.townbottari.domain.user.dto.TokenRefreshRequestDto;
import com.backend.townbottari.service.AuthService;
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
    public LoginResponseDto tokenRefresh(@RequestBody @Valid TokenRefreshRequestDto request) {
        return authService.tokenRefresh(request.getRefreshToken());

    }
}
