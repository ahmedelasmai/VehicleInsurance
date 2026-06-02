package com.home.vehicleinsurance.integration;

public interface S3StorageService {
    String uploadCsv(String fileName, String csvContent);
}
