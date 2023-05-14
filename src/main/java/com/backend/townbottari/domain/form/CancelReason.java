package com.backend.townbottari.domain.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CancelReason {
    AGREEMENT("AGREEMENT"),
    ERROR("ERROR");

    private final String value;
}