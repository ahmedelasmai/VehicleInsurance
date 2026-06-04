package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.dto.ComplianceSummaryDto;
import com.home.vehicleinsurance.dto.ViolationDto;
import com.home.vehicleinsurance.entity.*;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.PolicyRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComplianceService {

    private final VehicleRepository vehicleRepository;
    private final PolicyRepository policyRepository;
    private final MovementRepository movementRepository;

    public ComplianceService(VehicleRepository vehicleRepository,
                             PolicyRepository policyRepository,
                             MovementRepository movementRepository) {
        this.vehicleRepository = vehicleRepository;
        this.policyRepository = policyRepository;
        this.movementRepository = movementRepository;
    }

    public List<ViolationDto> runComplianceCheck() {

        LocalDate today = LocalDate.now();
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<ViolationDto> violations = new ArrayList<>();

        for (Vehicle v : vehicles) {

            List<Movement> movements =
                    movementRepository.findByVehicleIdOrderByMovementTimeAsc(v.getId());

            Optional<Policy> policyOpt =
                    policyRepository.findActiveByVehicleId(v.getId());

            // RULE 1 — NO POLICY
            if (policyOpt.isEmpty()) {
                violations.add(new ViolationDto(
                        v.getId(),
                        v.getRegistrationNumber(),
                        v.getOwnerName(),
                        ViolationType.NO_POLICY,
                        null,
                        null,
                        null
                ));
                continue;
            }

            Policy policy = policyOpt.get();

            boolean expired = policy.getExpiryDate().isBefore(today);

            // RULE 2 — POLICY EXPIRED
            if (expired) {
                violations.add(new ViolationDto(
                        v.getId(),
                        v.getRegistrationNumber(),
                        v.getOwnerName(),
                        ViolationType.POLICY_EXPIRED,
                        policy.getExpiryDate(),
                        null,
                        null
                ));
            }

            // RULE 3 — USED AFTER EXPIRY
            if (!movements.isEmpty()) {
                LocalDateTime expiryEndOfDay =
                        policy.getExpiryDate().atTime(23, 59, 59);

                for (Movement m : movements) {
                    if (m.getMovementTime().isAfter(expiryEndOfDay)) {
                        violations.add(new ViolationDto(
                                v.getId(),
                                v.getRegistrationNumber(),
                                v.getOwnerName(),
                                ViolationType.USED_AFTER_EXPIRY,
                                policy.getExpiryDate(),
                                m.getMovementTime(),
                                null
                        ));
                        break;
                    }
                }
            }
        }

        return violations;
    }

    public List<ViolationDto> getViolations() {
        return runComplianceCheck();
    }

    public ComplianceSummaryDto getSummary() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        List<ViolationDto> violations = runComplianceCheck();
        return new ComplianceSummaryDto(vehicles.size(), violations.size());
    }
}
