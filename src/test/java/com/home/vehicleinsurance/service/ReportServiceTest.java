package com.home.vehicleinsurance.service;


import org.junit.jupiter.api.Test;




import static org.junit.jupiter.api.Assertions.*;


import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Test
    void generateMonthlyCsv_shouldReturnData() {

        var month = java.time.YearMonth.now();

        byte[] result = reportService.generateMonthlyCsv(month);

        assertNotNull(result);

        String csv = new String(result);


        assertTrue(csv.contains("movementType"));
    }
}
