package com.home.vehicleinsurance.controller;


import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.service.ReportService;
import com.home.vehicleinsurance.service.TempVehicleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private TempVehicleDataService tempVehicleDataService;

    @Autowired
    private ReportService reportService;

    @GetMapping("/download")
    public ResponseEntity<byte []> downloadCsv() {
        List<Vehicle> vehicles = tempVehicleDataService.getVehicles();

        byte[] csv = reportService.generateCsv(vehicles);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report-" + LocalDate.now() + ".csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }


}


