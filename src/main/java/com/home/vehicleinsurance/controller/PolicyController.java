package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.entity.Policy;
import com.home.vehicleinsurance.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/policies")
@CrossOrigin
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // ADMIN ONLY — create a new policy for a vehicle
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Policy> createPolicy(@PathVariable Long vehicleId,
                                               @Valid @RequestBody Policy policy) {
        Policy created = policyService.createPolicy(vehicleId, policy);
        return ResponseEntity.ok(created);
    }

    // ADMIN + USER — get active policy for a vehicle
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Policy> getPolicyByVehicle(@PathVariable Long vehicleId) {
        return policyService.getPolicyByVehicleId(vehicleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ADMIN ONLY — update a policy
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{policyId}")
    public ResponseEntity<Policy> updatePolicy(@PathVariable Long policyId,
                                               @Valid @RequestBody Policy policy) {
        Policy updated = policyService.updatePolicy(policyId, policy);
        return ResponseEntity.ok(updated);
    }
}
