package com.home.vehicleinsurance.controller;


import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.service.ReportService;

import com.home.vehicleinsurance.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/download/{date}")
    public ResponseEntity<byte []> downloadCsv(@PathVariable String date) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        byte[] csv = reportService.generateCsv(vehicles);

//        2026-06-01, this format for date
        String filename = "report-" + date + ".csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }


}


