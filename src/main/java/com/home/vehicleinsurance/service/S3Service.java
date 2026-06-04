package com.home.vehicleinsurance.service;


import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.time.LocalDate;

@Service
public class S3Service {

    private final S3Client s3Client;

    String bucketName = "vehicle-reports-43535";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadReport(byte[] csv, LocalDate date) {

        String key = "reports/report-" + date + ".csv";

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("text/csv")
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(csv));
    }

    public byte[] downloadReport(LocalDate date) {

        String key = "reports/report-" + date + ".csv";

        return s3Client.getObjectAsBytes(
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build()
        ).asByteArray();
    }
}


