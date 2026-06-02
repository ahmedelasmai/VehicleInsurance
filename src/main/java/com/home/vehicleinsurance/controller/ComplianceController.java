package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.dto.ComplianceViolationDto;
import com.home.vehicleinsurance.service.ComplianceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
public class ComplianceController {

    private final ComplianceService complianceService;
    public ComplianceController(ComplianceService complianceService) {

        this.complianceService = complianceService;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @PostMapping("/run")
    public List<ComplianceViolationDto> runComplianceCheck() {
        return complianceService.runComplianceCheck();

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @GetMapping("/violations")
    public List<ComplianceViolationDto> violations() {
        return complianceService.getViolations();
    }

}


