package com.readme.api.service;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends RuntimeException {

    private final Object[] messageParams;

    private final int errorCode;

    public NotFoundException(int errorCode, Object... params) {
        this.errorCode = errorCode;
        this.messageParams = params;
    }

    public NotFoundException(String message, int errorCode, Object... params) {
        super(message);
        this.messageParams = params;
        this.errorCode = errorCode;
    }
}