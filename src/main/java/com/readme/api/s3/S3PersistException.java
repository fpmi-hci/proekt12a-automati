package com.readme.api.s3;

public class S3PersistException extends RuntimeException {
    public S3PersistException() {
        super();
    }

    public S3PersistException(String message) {
        super(message);
    }

    public S3PersistException(String message, Throwable cause) {
        super(message, cause);
    }

    public S3PersistException(Throwable cause) {
        super(cause);
    }
}
