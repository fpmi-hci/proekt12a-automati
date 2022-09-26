package com.readme.api.service;

public class CartNotFoundException extends NotFoundException{
    private static final int ERROR_CODE = 40402;

    public CartNotFoundException(Object... params) {
        super(ERROR_CODE, params);
    }

    public CartNotFoundException(String message, Object... params) {
        super(message, ERROR_CODE, params);
    }
}
