package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.dto.ComplianceViolationDto;
import com.home.vehicleinsurance.entity.ComplianceViolation;
import com.home.vehicleinsurance.entity.ViolationType;
import com.home.vehicleinsurance.repository.ComplianceViolationRepository;
import com.home.vehicleinsurance.repository.InsurancePolicyRepository;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplianceServiceImpl implements ComplianceService {

    private final VehicleRepository vehicleRepository;
    private final InsurancePolicyRepository policyRepository;
    private final ComplianceViolationRepository violationRepository;
    private final MovementRepository movementRepository;
    private final ViolationNotifier violationNotifier;

    @Override
    public List<ComplianceViolationDto> runComplianceCheck() {
        return vehicleRepository.findAll().stream().flatMap(vehicle -> {
            boolean hasValidPolicy =
                    policyRepository.findByVehicleId(vehicle.getId())
                            .filter(p -> !p.getExpiryDate().isBefore(LocalDate.now()))
                            .isPresent();

            if (hasValidPolicy) {
                ComplianceViolation violation = new ComplianceViolation();
                violation.setVehicle(vehicle);
                violation.setViolationType(ViolationType.EXPIRED_POLICY);
                violation.setDetails("Insurance Policy Expired");
                violation.setViolationTime(LocalDateTime.now());
                violation.setResolved(false);

                ComplianceViolation saved = violationRepository.save(violation);
                return java.util.stream.Stream.of(toResponse(saved));
            }
            return java.util.stream.Stream.<ComplianceViolationDto>empty();
        }).toList();
    }

    @Override
    public List<ComplianceViolationDto> getViolations() {
        return violationRepository.findByResolvedFalse()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ComplianceViolationDto toResponse(ComplianceViolation v) {
        return new ComplianceViolationDto(
                v.getId(),
                v.getVehicle().getId(),
                v.getViolationType().name(),
                v.getViolationTime(),
                v.getDetails(),
                v.getVehicle().getRegistrationNumber()

        );
    }

}

