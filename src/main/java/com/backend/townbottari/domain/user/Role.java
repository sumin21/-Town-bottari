package com.backend.townbottari.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    KAKAO("ROLE_KAKAO"),
    ADMIN("ROLE_ADMIN");

    private final String value;
}