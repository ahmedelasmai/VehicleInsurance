package com.home.vehicleinsurance.controller;


import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.service.ReportService;

import com.home.vehicleinsurance.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReportService reportService;


    @PostMapping("generate")
    public ResponseEntity<String> generateReport() {
        reportService.generateAndUploadReport();
        return ResponseEntity.ok("Uploaded to S3");
    }


    @GetMapping("download/{date}")
    public ResponseEntity<byte[]> downloadReport(@PathVariable String date) {

        LocalDate parsedDate = LocalDate.parse(date);

        byte[] file = reportService.downloadReport(parsedDate);

        return ResponseEntity.ok()
                .header("Content-Disposition",
                        "attachment; filename=report-" + date + ".csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(file);
    }


}


