package com.example.vehicle_insurance.service;

import com.example.vehicle_insurance.dto.ReportSummaryResponse;

import java.time.LocalDate;

public interface ReportService {
    ReportSummaryResponse getSummary();
    String generateAndUploadMonthlyReport(LocalDate date);
    byte[] downloadReport(LocalDate date);


}
