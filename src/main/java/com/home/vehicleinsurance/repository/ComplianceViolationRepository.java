package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.ComplianceViolation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplianceViolationRepository extends JpaRepository<ComplianceViolation, Long> {
    List<ComplianceViolation> findByVehicleId(Long vehicleId);
    List<ComplianceViolation> findByResolvedFalse();
}
