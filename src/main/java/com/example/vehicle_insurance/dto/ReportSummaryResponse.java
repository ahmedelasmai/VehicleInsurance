package com.example.vehicle_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ReportSummaryResponse {
    private int totalVehicles;
    private int compliantVehicles;
    private int violatingVehicles;


}
