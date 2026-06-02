package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.dto.InsurancePolicyRequest;
import com.home.vehicleinsurance.dto.InsurancePolicyResponse;
import com.home.vehicleinsurance.service.PolicyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @PostMapping
    public InsurancePolicyResponse create(
            @RequestBody InsurancePolicyRequest request) {
        return policyService.createPolicy(request);

    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @PutMapping("/{policyId}")
    public InsurancePolicyResponse update(
            @PathVariable Long policyId,
            @RequestBody InsurancePolicyRequest request) {
        return policyService.updatePolicy(policyId, request);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER') ")
    @GetMapping("{vehicleId}")
    public InsurancePolicyResponse getPolicyByVehicle(
            @PathVariable Long vehicleId) {
        return policyService.getPolicyByVehicle(vehicleId);
    }

}

