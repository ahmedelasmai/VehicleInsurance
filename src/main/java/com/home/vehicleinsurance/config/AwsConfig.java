package com.home.vehicleinsurance.config;

import org.hibernate.cache.spi.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class AwsConfig {
    @Value("${aws.region}")
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();

    }
}
