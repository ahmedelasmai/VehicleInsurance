package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.ComplianceViolation;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.entity.ViolationType;
import com.home.vehicleinsurance.service.ComplianceService;
import com.home.vehicleinsurance.service.ComplianceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class ComplianceServiceTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private InsurancePolicyRepository insurancePolicyRepository;
    @Mock
    private ComplianceServiceImpl violationRepository;

    @InjectMocks
    private ComplianceServiceImpl complianceService;

    @Test
    void shouldCreateViolationWhenPolicyMissing() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("NO-POLICY");
        vehicle.setId(1L);

        when(vehicleRepository.findAll()).thenReturn(List.of(vehicle));
        when(insurancePolicyRepository.findByVehicleId(1L)).thenReturn(Optional.empty());

        ComplianceViolation saved = new ComplianceViolation();
        saved.setId(100L);
        saved.setVehicle(vehicle);
        saved.setDetails("Insurance policy is missing or expired");

        when(violationRepository.save(any(ComplianceViolation.class))).thenReturn(saved);

        var result = complianceService.runComplianceCheck();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getVichleId()).isEqualTo(1L);
        verify(violationRepository, times(1)).save(any(ComplianceViolation.class));
    }
    @Test
    void shouldReturnUnresolvedViolations() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setId(1L);

        ComplianceViolation violation = new ComplianceViolation();
        violation.setId(200L);
        violation.setVehicle(vehicle);
        violation.setDetails("Insurance policy expired");
        violation.setResolved(false);

        when(violationRepository.findByResolvedFalse()).thenReturn(List.of(violation));
        var result = complianceService.getViolations();
    }
}
