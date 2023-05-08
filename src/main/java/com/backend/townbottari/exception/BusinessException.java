package com.backend.townbottari.exception;

public class BusinessException extends RuntimeException {

    private final ExceptionCode errorCode;

    public BusinessException(String message, ExceptionCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(ExceptionCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ExceptionCode getErrorCode() {
        return errorCode;
    }

}