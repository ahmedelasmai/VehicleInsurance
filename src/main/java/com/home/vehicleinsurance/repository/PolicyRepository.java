package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    @Query("select p from Policy p where p.vehicle.id = :vehicleId and p.status = 'ACTIVE'")
    Optional<Policy> findActiveByVehicleId(@Param("vehicleId") Long vehicleId);

    @Query("select p from Policy p where p.expiryDate < :today and p.status = 'ACTIVE'")
    List<Policy> findExpiredPolicies(@Param("today") LocalDate today);
}
