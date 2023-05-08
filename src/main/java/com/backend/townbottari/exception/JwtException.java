package com.backend.townbottari.exception;

public class JwtException extends BusinessException {

    public JwtException() {
        super(ExceptionCode.JWT_EXCEPTION);
    }
}