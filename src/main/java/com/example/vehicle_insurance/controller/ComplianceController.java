package com.example.vehicle_insurance.controller;


import com.example.vehicle_insurance.dto.ComplianceViolationDto;
import com.example.vehicle_insurance.service.ComplianceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

