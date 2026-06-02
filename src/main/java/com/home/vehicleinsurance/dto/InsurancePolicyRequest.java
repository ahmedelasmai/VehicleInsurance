package com.home.vehicleinsurance.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class InsurancePolicyRequest {
    private Long vehicleId;
    private String policyNumber;
    private String policyType;
    private String provider;
    private LocalDate issueDate;
    private LocalDate expiryDate;

}

