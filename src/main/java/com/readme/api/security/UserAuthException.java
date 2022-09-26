package com.readme.api.security;

import org.springframework.security.core.AuthenticationException;

public class UserAuthException extends AuthenticationException {

    private int errorCode;

    public UserAuthException(String explanation) {
        super(explanation);
    }

    public UserAuthException(String explanation, int errorCode) {
        super(explanation);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}