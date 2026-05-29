package com.example.vehicle_insurance.service;

import com.example.vehicle_insurance.dto.InsurancePolicyRequest;
import com.example.vehicle_insurance.dto.InsurancePolicyResponse;


public interface PolicyService {
    InsurancePolicyResponse createPolicy(InsurancePolicyRequest request);
    InsurancePolicyResponse updatePolicy(Long id, InsurancePolicyRequest request);

    InsurancePolicyResponse getPolicyByVehicle(Long vehicleId);

    }
