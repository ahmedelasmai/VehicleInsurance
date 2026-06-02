package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.dto.InsurancePolicyRequest;
import com.home.vehicleinsurance.dto.InsurancePolicyResponse;
import com.home.vehicleinsurance.entity.InsurancePolicy;
import com.home.vehicleinsurance.entity.PolicyStatus;
import com.home.vehicleinsurance.entity.PolicyType;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.service.PolicyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyServiceTest {
     @Mock
    private InsurancePolicyRepository policyRepository;
     @Mock
    private VehicleRepository vehicleRepository;
     @InjectMocks
     private PolicyServiceImpl policyService;
     private Vehicle vehicle;

     @Test
    void shouldCreateActivePolicyWhenExpiryIsFuture() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setId(1L);


         InsurancePolicyRequest request = new InsurancePolicyRequest();
         request.setPolicyNumber("POL001");
         request.setPolicyType("Comprehensive");
         request.setIssueDate(LocalDate.now().minusDays(1));
         request.setExpiryDate(LocalDate.now().plusDays(10));
         request.setVehicleId(1L);

         when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));

         when(policyRepository.save(any(InsurancePolicy.class))).thenAnswer(invocation -> invocation.getArgument(0));

         InsurancePolicyResponse response = policyService.createPolicy(request);
         assertThat(response.getStatus()).isEqualTo("Active");
         assertThat(response.getPolicyNumber()).isEqualTo("POL001");
         verify(policyRepository, times(1)).save(any(InsurancePolicy.class));
    }

    @Test
    void shouldCreateExpiredPolicyWhenExpiryIsPast() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setId(1L);

        InsurancePolicyRequest request = new InsurancePolicyRequest();
        request.setPolicyNumber("POL002");
        request.setPolicyType("THIRD_PARTY");
        request.setIssueDate(LocalDate.now().minusDays(10));
        request.setExpiryDate(LocalDate.now().minusDays(1));
        request.setVehicleId(1L);

        when(vehicleRepository.findById(any())).thenReturn(Optional.of(vehicle));

        when(policyRepository.save(any(InsurancePolicy.class))).thenAnswer(invocation -> invocation.getArgument(0));

        InsurancePolicyResponse response = policyService.createPolicy(request);
        assertThat(response.getStatus()).isEqualTo("Expired");
    }
    @Test
    void shouldReturnPolicyByVehicle() {
         Vehicle vehicle = new Vehicle();
         vehicle.setRegistrationNumber("ABC123");
         vehicle.setId(1L);

         InsurancePolicy policy = new InsurancePolicy();
         policy.setPolicyNumber("POL003");
         policy.setPolicyType(PolicyType.COMPREHENSIVE);
         policy.setIssueDate(LocalDate.now().minusDays(1));
         policy.setExpiryDate(LocalDate.now().plusDays(1));
         policy.setStatus(PolicyStatus.ACTIVE);
         policy.setVehicle(vehicle);

         when(policyRepository.findByVehicleId(1L)).thenReturn(Optional.of(policy));

         InsurancePolicyResponse response = policyService.getPolicyByVehicle(1L);

        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getVehicleId()).isEqualTo(1L);
         assertThat(response.getPolicyNumber()).isEqualTo("POL003");

    }
}
