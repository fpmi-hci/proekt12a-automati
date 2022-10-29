package com.readme.api.s3;

public class S3Info {
    private final String bucketName;
    private final String objectKey;

    public S3Info(String bucketName, String objectKey) {
        this.bucketName = bucketName;
        this.objectKey = objectKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void listBucket(){

    }
}
