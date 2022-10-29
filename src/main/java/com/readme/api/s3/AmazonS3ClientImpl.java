package com.readme.api.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.readme.api.service.exception.BucketNotFoundError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AmazonS3ClientImpl implements AmazonS3Client {
    private final AmazonS3 amazonS3;

    @Override
    public PutObjectResult persistContent(InputStream content, S3Info s3Info) {
        String bucketName = s3Info.getBucketName();
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            throw new BucketNotFoundError(bucketName);
        }
        String key = s3Info.getObjectKey();
        File contentFile = new File(key);
        try {
            FileUtils.copyInputStreamToFile(content, contentFile);
        } catch (IOException e) {
            throw new S3PersistException(e.getMessage(), e);
        }
        return amazonS3.putObject(bucketName, key, contentFile);
    }

    @Override
    public S3Object getFileFromS3(S3Info s3Info) {
        return amazonS3.getObject(new GetObjectRequest(s3Info.getBucketName(), s3Info.getObjectKey()));
    }
}
