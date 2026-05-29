package com.example.vehicle_insurance.service.impl;

import com.example.vehicle_insurance.dto.InsurancePolicyRequest;
import com.example.vehicle_insurance.dto.InsurancePolicyResponse;
import com.example.vehicle_insurance.entity.InsurancePolicy;
import com.example.vehicle_insurance.entity.Vehicle;
import com.example.vehicle_insurance.enums.PolicyStatus;
import com.example.vehicle_insurance.enums.PolicyType;
import com.example.vehicle_insurance.exception.ResourceNotFoundException;
import com.example.vehicle_insurance.repository.InsurancePolicyRepository;
import com.example.vehicle_insurance.service.PolicyService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {

    private final InsurancePolicyRepository policyRepository;
    private final VehicleRepository vehicleRepository;

    public PolicyServiceImpl(InsurancePolicyRepository policyRepository,
                             VehicleRepository vehicleRepository) {
        this.policyRepository = policyRepository;
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public InsurancePolicyResponse createPolicy(InsurancePolicyRequest request) {
               Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        InsurancePolicy policy = new InsurancePolicy();
        policy.setVehicle(vehicle);
        policy.setPolicyNumber(request.getPolicyNumber());
        policy.setPolicyType(PolicyType.valueOf(request.getPolicyType()));
        policy.setIssueDate(request.getIssueDate());
        policy.setExpiryDate(request.getExpiryDate());
        policy.setProvider(request.getProvider());
        policy.setActive(true);
        policy.setStatus(request.getExpiryDate().isBefore(LocalDate.now()) ?
                PolicyStatus.EXPIRED : PolicyStatus.ACTIVE);

        InsurancePolicy saved = policyRepository.save(policy);
        return toResponse(saved);
    }

    @Override
    public InsurancePolicyResponse updatePolicy(Long policyId, InsurancePolicyRequest request) {
        InsurancePolicy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new EntityNotFoundException("Policy not found"));
        if (policy.getExpiryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot update expired policy");
        }
        validateDates(request.getIssueDate(), request.getExpiryDate());
        policy.setPolicyNumber(request.getPolicyNumber());
        policy.setPolicyType(PolicyType.valueOf(request.getPolicyType()));
        policy.setIssueDate(request.getIssueDate());
        policy.setExpiryDate(request.getExpiryDate());
        policy.setProvider(request.getProvider());

        return toResponse(policy);
    }

    @Override
    @Transactional(readOnly = true)
    public InsurancePolicyResponse getPolicyByVehicle(Long vehicleId) {
        InsurancePolicy policy = policyRepository.findByVehicleId(vehicleId)
                .orElseThrow(() -> new EntityNotFoundException("Active policy not found"));
        return toResponse(policy);
    }

    private void validateDates(LocalDate issueDate, LocalDate expiryDate) {
        if (expiryDate.isBefore(issueDate)) {
            throw new IllegalStateException("Expiry date must be after issue date");
        }
    }

    private InsurancePolicyResponse toResponse(InsurancePolicy policy) {
        InsurancePolicyResponse response = new InsurancePolicyResponse();
        response.setId(policy.getId());
        response.setPolicyNumber(policy.getPolicyNumber());
        response.setIssueDate(policy.getIssueDate());
        response.setExpiryDate(policy.getExpiryDate());
        response.setProvider(policy.getProvider());
        response.setVehicleId(policy.getVehicle().getId());
        response.setPolicyType(policy.getPolicyType().name());
        response.setProvider(policy.getProvider());

        return response;
    }
}









