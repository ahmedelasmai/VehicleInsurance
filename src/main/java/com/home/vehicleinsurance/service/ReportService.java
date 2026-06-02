package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.dto.ReportSummaryResponse;

import java.time.LocalDate;

public interface ReportService {
    ReportSummaryResponse getSummary();
    String generateAndUploadMonthlyReport(LocalDate date);
    byte[] downloadReport(LocalDate date);


}
