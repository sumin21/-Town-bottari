package com.backend.townbottari.domain.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum CancelPosition {
    CONSUMER("CONSUMER"),
    DELIVERER("DELIVERER");

    private final String value;
}