package com.readme.api.service;

public class UserNotFoundException extends NotFoundException {
    private static final int ERROR_CODE = 40401;

    public UserNotFoundException(Object... params) {
        super(ERROR_CODE, params);
    }

    public UserNotFoundException(String message, Object... params) {
        super(message, ERROR_CODE, params);
    }
}