package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.*;
import com.home.vehicleinsurance.repository.PolicyRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final VehicleRepository vehicleRepository;

    public PolicyService(PolicyRepository policyRepository,
                         VehicleRepository vehicleRepository) {
        this.policyRepository = policyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Policy createPolicy(Long vehicleId, Policy policy) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found: " + vehicleId));

        // Validate dates
        if (policy.getIssueDate() == null || policy.getExpiryDate() == null) {
            throw new IllegalArgumentException("Issue date and expiry date are required");
        }
        if (policy.getExpiryDate().isBefore(policy.getIssueDate())) {
            throw new IllegalArgumentException("Expiry date cannot be before issue date");
        }

        // Deactivate existing active policy
        policyRepository.findActiveByVehicleId(vehicleId)
                .ifPresent(existing -> {
                    existing.setStatus(PolicyStatus.EXPIRED);
                    policyRepository.save(existing);
                });

        // Assign vehicle
        policy.setVehicle(vehicle);

        // Default status
        policy.setStatus(PolicyStatus.ACTIVE);

        // Generate policy number if missing
        if (policy.getPolicyNumber() == null || policy.getPolicyNumber().isBlank()) {
            policy.setPolicyNumber("POL-" + System.currentTimeMillis());
        }

        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Long policyId, Policy updated) {

        Policy existing = policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Policy not found: " + policyId));

        existing.setPolicyType(updated.getPolicyType());
        existing.setIssueDate(updated.getIssueDate());
        existing.setExpiryDate(updated.getExpiryDate());
        existing.setPolicyNumber(updated.getPolicyNumber());

        // Auto-update status based on expiry
        if (updated.getExpiryDate() != null &&
                updated.getExpiryDate().isBefore(LocalDate.now())) {
            existing.setStatus(PolicyStatus.EXPIRED);
        }

        // Allow manual override
        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }

        return policyRepository.save(existing);
    }

    public Optional<Policy> getPolicyByVehicleId(Long vehicleId) {
        return policyRepository.findActiveByVehicleId(vehicleId);
    }
}
