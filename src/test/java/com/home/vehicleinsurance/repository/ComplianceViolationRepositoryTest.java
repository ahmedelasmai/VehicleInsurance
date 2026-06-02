package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.ComplianceViolation;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.entity.ViolationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ComplianceViolationRepositoryTest {
    @Autowired
    private ComplianceViolationRepository violationRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Test
    void shouldFindUnresolvedViolation() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("J-500");
        vehicle.setOwnerName("Jack");
        vehicle.setVehicleType("TRUCK");
        vehicleRepository.save(vehicle);

        ComplianceViolation violation = new ComplianceViolation();
        violation.setVehicle(vehicle);
        violation.setViolationType(ViolationType.EXPIRED_POLICY);
        violation.setDetails("Expired policy");
        violation.setResolved(false);
        violation.setViolationTime(LocalDateTime.now());
        violationRepository.save(violation);

        List<ComplianceViolation> unresolved =
                violationRepository.findByResolvedFalse();

        assertThat(unresolved).hasSize(1);
        assertThat(unresolved.get(0).getViolationType()).isEqualTo(ViolationType.EXPIRED_POLICY);
    }
}
