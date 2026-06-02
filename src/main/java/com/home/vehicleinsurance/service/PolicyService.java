package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.dto.InsurancePolicyRequest;
import com.home.vehicleinsurance.dto.InsurancePolicyResponse;

public interface PolicyService {
    InsurancePolicyResponse createPolicy(InsurancePolicyRequest request);
    InsurancePolicyResponse updatePolicy(Long id, InsurancePolicyRequest request);

    InsurancePolicyResponse getPolicyByVehicle(Long vehicleId);

}
