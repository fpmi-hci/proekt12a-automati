package com.readme.api.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3Configurer {
    private final AmazonS3Properties config;

    @Autowired
    public AmazonS3Configurer(AmazonS3Properties config) {
        this.config = config;
    }

    public AmazonS3 configure() {
        String awsAccessKeyId = config.getAwsAccessKeyId();
        String awsSecretAccessKey = config.getAwsSecretAccessKey();
        String sessionToken = config.getSessionToken();
        AWSCredentials credentials = new BasicSessionCredentials(awsAccessKeyId, awsSecretAccessKey, sessionToken);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        return AmazonS3ClientBuilder.standard()
                .withRegion(config.getAwsRegion())
                .withCredentials(credentialsProvider)
                .build();
    }
}
