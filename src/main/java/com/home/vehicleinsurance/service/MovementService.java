package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.Movement;
import com.home.vehicleinsurance.entity.MovementType;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementService {

    private final MovementRepository movementRepository;
    private final VehicleRepository vehicleRepository;

    public MovementService(MovementRepository movementRepository,
                           VehicleRepository vehicleRepository) {
        this.movementRepository = movementRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Movement> getMovementsByVehicleId(Long vehicleId) {
        return movementRepository.findByVehicleIdOrderByMovementTimeAsc(vehicleId);
    }

    public Movement createMovement(Movement movement) {
        Vehicle vehicle = vehicleRepository.findById(
                movement.getVehicle().getId()
        ).orElseThrow(() -> new RuntimeException("Vehicle not found"));

        movement.setVehicle(vehicle);

        validateTripSequence(vehicle.getId(), movement);

        return movementRepository.save(movement);
    }

    private void validateTripSequence(Long vehicleId, Movement movement) {
        Movement latestMovement = movementRepository
                .findTopByVehicleIdOrderByMovementTimeDesc(vehicleId)
                .orElse(null);

        if (latestMovement == null) {
            if (movement.getMovementType() == MovementType.TRIP_END) {
                throw new RuntimeException("First movement must be TRIP_START");
            }

            return;
        }

        if (latestMovement.getMovementType() == movement.getMovementType()) {
            throw new RuntimeException("Invalid trip sequence");
        }
    }
}