package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.InsurancePolicy;
import com.home.vehicleinsurance.entity.PolicyStatus;
import com.home.vehicleinsurance.entity.PolicyType;
import com.home.vehicleinsurance.entity.Vehicle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsurancePolicyRepositoryTest {

    @Autowired
    private InsurancePolicyRepository insurancePolicyRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;

    @BeforeEach
    void setup() {
        vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC 234");
        vehicle.setOwnerName("John Paul");
        vehicle.setVehicleType("Car");
        vehicleRepository.save(vehicle);
    }

    @Test
    void shouldFindActivePolicyByVehicleId(){
        InsurancePolicy policy = new InsurancePolicy();
        policy.setVehicle(vehicle);
        policy.setPolicyNumber("POL-002");
        policy.setPolicyType(PolicyType.COMPREHENSIVE);
        policy.setProvider("Tesco");
        policy.setIssueDate(LocalDate.now().minusDays(10));
        policy.setExpiryDate(LocalDate.now().plusDays(10));
        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setActive(true);
        insurancePolicyRepository.save(policy);

        Optional<InsurancePolicy> found =
                insurancePolicyRepository.findByVehicleId(policy.getId());
              assertThat(found).isPresent();
              assertThat(found.get().getPolicyNumber()).isEqualTo("POL-002");

    }
    @Test
    void findValidPolicyByVehicleId(){
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("CDC234");
        vehicle.setOwnerName("Paul");
        vehicle.setVehicleType("Van");
        vehicleRepository.save(vehicle);

        InsurancePolicy policy = new InsurancePolicy();
        policy.setVehicle(vehicle);
        policy.setPolicyNumber("POL-003");
        policy.setPolicyType(PolicyType.THIRD_PARTY);
        policy.setProvider("Aviva");
        policy.setIssueDate(LocalDate.now().minusDays(20));
        policy.setExpiryDate(LocalDate.now().plusDays(20));
        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setActive(true);
        insurancePolicyRepository.save(policy);

        Optional<InsurancePolicy> foundPolicy =
                insurancePolicyRepository.findValidPolicyByVehicleId(vehicle.getId(), LocalDate.now());

        assertThat(foundPolicy).isPresent();
        assertThat(foundPolicy.get().getPolicyNumber()).isEqualTo("POL-003");
    }


}
