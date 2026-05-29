package com.example.vehicle_insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ComplianceViolationDto {
    private Long id;
    private Long vehicleId;
    private String violationType;
    private LocalDateTime violationTime;
    private String detail;
    private String registrationNumber;

}
