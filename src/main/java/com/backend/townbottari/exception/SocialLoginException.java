package com.backend.townbottari.exception;

public class SocialLoginException extends BusinessException {

    public SocialLoginException() {
        super(ExceptionCode.SOCIAL_LOGIN_FAIL);
    }
}