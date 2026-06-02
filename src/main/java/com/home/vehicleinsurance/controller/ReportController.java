package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.dto.ReportSummaryResponse;
import com.home.vehicleinsurance.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @GetMapping("/summary")
    public ReportSummaryResponse getSummary() {
        return reportService.getSummary();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @GetMapping("/download/{date}")
    public byte[] downloadReport(@PathVariable String date) {
        return reportService.downloadReport(LocalDate.parse(date));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @PostMapping("/generate/{date}")
    public String generate(@PathVariable LocalDate date)
    {
        return reportService.generateAndUploadMonthlyReport(date);
    }
}

