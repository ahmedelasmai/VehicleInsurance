package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovementRepository extends JpaRepository<Movement, Long> {

    List<Movement> findByVehicleIdOrderByMovementTimeAsc(Long vehicleId);

    Optional<Movement> findTopByVehicleIdOrderByMovementTimeDesc(Long vehicleId);
}