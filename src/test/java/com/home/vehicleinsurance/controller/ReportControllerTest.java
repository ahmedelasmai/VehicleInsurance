package com.home.vehicleinsurance.controller;



import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportControllerTest {

    @Autowired
    private ReportController reportController;

    @Test
    void generateReport_shouldReturnOk() {

        var response = reportController.generateReport();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Uploaded to S3", response.getBody());
    }
}

