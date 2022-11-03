package com.readme.api.s3;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    @Autowired
    private AmazonS3Configurer amazonS3Configurer;


    @Bean
    public AmazonS3 amazons3(){
        return amazonS3Configurer.configure();
    }
}
