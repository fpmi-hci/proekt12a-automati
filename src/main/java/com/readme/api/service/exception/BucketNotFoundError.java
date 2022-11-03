package com.readme.api.service.exception;

public class BucketNotFoundError extends Error {
    private static final String MESSAGE = "Bucket with name = %s doesn't exist";


    public BucketNotFoundError(String bucketName) {
        super(String.format(MESSAGE, bucketName));
    }
}
