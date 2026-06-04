package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.dto.ComplianceSummaryDto;
import com.home.vehicleinsurance.dto.ViolationDto;
import com.home.vehicleinsurance.service.ComplianceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compliance")
@CrossOrigin
public class ComplianceController {

    private final ComplianceService complianceService;

    public ComplianceController(ComplianceService complianceService) {
        this.complianceService = complianceService;
    }

    // ADMIN ONLY — run compliance check
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/run")
    public ResponseEntity<List<ViolationDto>> runComplianceCheck() {
        List<ViolationDto> violations = complianceService.runComplianceCheck();
        return violations.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(violations);
    }

    // ADMIN + USER — get all violations
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/violations")
    public ResponseEntity<List<ViolationDto>> getViolations() {
        List<ViolationDto> violations = complianceService.getViolations();
        return violations.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(violations);
    }

    // ADMIN + USER — get summary
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/summary")
    public ResponseEntity<ComplianceSummaryDto> getSummary() {
        return ResponseEntity.ok(complianceService.getSummary());
    }
}
