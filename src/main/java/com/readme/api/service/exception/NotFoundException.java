package com.readme.api.service.exception;

import lombok.Getter;

@Getter
public abstract class NotFoundException extends RuntimeException {

    private final Object[] messageParams;

    public NotFoundException(String message, Object... params) {
        super(String.format(message, params));
        this.messageParams = params;
    }


}