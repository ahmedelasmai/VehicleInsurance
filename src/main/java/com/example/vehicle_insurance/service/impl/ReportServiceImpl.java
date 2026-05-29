package com.example.vehicle_insurance.service.impl;

import com.example.vehicle_insurance.dto.ReportSummaryResponse;
import com.example.vehicle_insurance.entity.ReportMetadata;
import com.example.vehicle_insurance.integration.S3StorageService;
import com.example.vehicle_insurance.repository.ComplianceViolationRepository;
import com.example.vehicle_insurance.repository.ReportMetadataRepository;
import com.example.vehicle_insurance.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final VehicleRepository vehicleRepository;
    private final ComplianceViolationRepository violationRepository;
    private final ReportMetadataRepository reportMetadataRepository;
    private final S3StorageService s3StorageService;

    @Override
    public ReportSummaryResponse getSummary() {
        int total = (int) vehicleRepository.count();
        int violating = violationRepository.findByResolvedFalse().size();
        int compliant = total - violating;
        return new ReportSummaryResponse(total, compliant, violating);
    }
    @Override
    public String generateAndUploadMonthlyReport(LocalDate date) {
        ReportSummaryResponse summary = getSummary();

        String csv =
                "reportDate, totalVehicles, compliantVehicles, violatingVehicles\n"
                + date + ","
                + summary.getTotalVehicles() + ","
                + summary.getCompliantVehicles() + ","
                + summary.getViolatingVehicles() + "\n";

        String fileName = "compliance-report-" + date + ".csv";
        String s3Key=s3StorageService.uploadCsv(fileName, csv);

        ReportMetadata metadata = new ReportMetadata();
        metadata.setReportDate(date);
        metadata.setTotalVehicles(summary.getTotalVehicles());
        metadata.setCompliantVehicles(summary.getCompliantVehicles());
        metadata.setViolatingVehicles(summary.getViolatingVehicles());
        metadata.setMonth(date.getYear() + "_" + date.getMonthValue());
        metadata.setS3ObjectKey(s3Key);
        metadata.setGeneratedAt(LocalDateTime.now());
        reportMetadataRepository.save(metadata);
        return s3Key;
    }
    @Override
    public byte[] downloadReport(LocalDate date) {
        String csv =
                "reportDate, totalVehicles, compliantVehicles, violatingVehicles\n"
                + date + ",0,0,0\n";
        return csv.getBytes(StandardCharsets.UTF_8);
    }
}
