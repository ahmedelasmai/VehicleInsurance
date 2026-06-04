package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.entity.Policy;
import com.home.vehicleinsurance.entity.PolicyType;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.PolicyRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import com.home.vehicleinsurance.service.PolicyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PolicyServiceTest {

    @Autowired
    private PolicyService policyService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Test
    void createPolicy_savesPolicyCorrectly() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG100");
        v.setOwnerName("Alice");
        v.setVehicleType("VAN");
        vehicleRepository.save(v);

        Policy p = new Policy();
        p.setPolicyNumber("POL100");
        p.setPolicyType(PolicyType.COMPREHENSIVE);
        p.setIssueDate(LocalDate.now());
        p.setExpiryDate(LocalDate.now().plusDays(30));

        Policy created = policyService.createPolicy(v.getId(), p);

        assertThat(created.getId()).isNotNull();
        assertThat(created.getVehicle().getId()).isEqualTo(v.getId());
    }

    @Test
    void getPolicyByVehicleId_returnsPolicy() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG200");
        v.setOwnerName("Sam");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        Policy p = new Policy();
        p.setVehicle(v);
        p.setPolicyNumber("POL200");
        p.setPolicyType(PolicyType.THIRD_PARTY);
        p.setIssueDate(LocalDate.now());
        p.setExpiryDate(LocalDate.now().plusDays(20));
        policyRepository.save(p);

        Optional<Policy> result = policyService.getPolicyByVehicleId(v.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getPolicyNumber()).isEqualTo("POL200");
    }
}

