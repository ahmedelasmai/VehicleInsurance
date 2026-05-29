package com.example.vehicle_insurance.repository;

import com.example.vehicle_insurance.entity.InsurancePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface InsurancePolicyRepository extends JpaRepository<InsurancePolicy, Long> {
    Optional<InsurancePolicy> findByVehicleId(Long vehicleId);
    Optional<InsurancePolicy> findByPolicyNumber(String policyNumber);

    @Query("""
SELECT p FROM InsurancePolicy p
WHERE p.vehicle.id = :vehicleId
AND p.expiryDate >= :date
""")
    Optional<InsurancePolicy> findValidPolicyByVehicleId(Long vehicleId,  LocalDate date);


}
