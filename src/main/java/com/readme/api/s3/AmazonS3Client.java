package com.readme.api.s3;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

import java.io.InputStream;


public interface AmazonS3Client {

    PutObjectResult persistContent(InputStream content, S3Info s3Info);

    S3Object getFileFromS3(S3Info s3Info);

}