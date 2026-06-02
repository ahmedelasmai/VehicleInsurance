package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.dto.ComplianceViolationDto;

import java.util.List;

public interface ComplianceService {

    List<ComplianceViolationDto> runComplianceCheck();

    List<ComplianceViolationDto> getViolations();
}
