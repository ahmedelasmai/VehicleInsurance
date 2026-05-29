package com.example.vehicle_insurance.repository;

import com.example.vehicle_insurance.entity.ComplianceViolation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceViolationRepository extends JpaRepository<ComplianceViolation, Long> {
    List<ComplianceViolation> findByVehicleId(Long vehicleId);
    List<ComplianceViolation> findByResolvedFalse();
}
