package com.example.vehicle_insurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class InsurancePolicyResponse {
    private Long id;
    private Long vehicleId;
    private String policyNumber;
    private String policyType;
    private String provider;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;

}
