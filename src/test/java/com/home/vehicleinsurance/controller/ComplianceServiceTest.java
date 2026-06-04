package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.dto.ViolationDto;
import com.home.vehicleinsurance.entity.*;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.PolicyRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import com.home.vehicleinsurance.service.ComplianceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest

class ComplianceServiceTest {

    @Autowired
    private ComplianceService complianceService;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Test
    void violation_whenNoPolicy() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG300");
        v.setOwnerName("NoPolicyOwner");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        Movement start = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusDays(1),
                v
        );
        movementRepository.save(start);

        List<ViolationDto> result = complianceService.runComplianceCheck();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getViolationType()).isEqualTo(ViolationType.NO_POLICY);
    }

    @Test
    void violation_whenPolicyExpired() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG400");
        v.setOwnerName("ExpiredOwner");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        Policy p = new Policy();
        p.setVehicle(v);
        p.setPolicyNumber("POL400");
        p.setPolicyType(PolicyType.COMPREHENSIVE);
        p.setIssueDate(LocalDate.now().minusDays(30));
        p.setExpiryDate(LocalDate.now().minusDays(1));
        p.setStatus(PolicyStatus.ACTIVE);
        policyRepository.save(p);

        Movement start = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusDays(2),
                v
        );
        movementRepository.save(start);

        List<ViolationDto> result = complianceService.runComplianceCheck();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getViolationType()).isEqualTo(ViolationType.POLICY_EXPIRED);
    }

    @Test
    void violation_whenUsedAfterExpiry() {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG500");
        v.setOwnerName("LateDriver");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        Policy p = new Policy();
        p.setVehicle(v);
        p.setPolicyNumber("POL500");
        p.setPolicyType(PolicyType.THIRD_PARTY);
        p.setIssueDate(LocalDate.now().minusDays(20));
        p.setExpiryDate(LocalDate.now().minusDays(5));
        p.setStatus(PolicyStatus.ACTIVE);
        policyRepository.save(p);

        Movement start = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusDays(1),
                v
        );
        movementRepository.save(start);

        List<ViolationDto> result = complianceService.runComplianceCheck();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getViolationType()).isEqualTo(ViolationType.USED_AFTER_EXPIRY);
    }
}

