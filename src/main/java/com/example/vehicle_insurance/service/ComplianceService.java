package com.example.vehicle_insurance.service;

import com.example.vehicle_insurance.dto.ComplianceViolationDto;

import java.util.List;


public interface ComplianceService {

    List<ComplianceViolationDto> runComplianceCheck();

    List<ComplianceViolationDto> getViolations();
}
