package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.Movement;
import com.home.vehicleinsurance.entity.MovementType;
import com.home.vehicleinsurance.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MovementRepositoryTest {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void findByVehicleIdOrderByMovementTimeAsc_shouldReturnMovements() {

        Vehicle vehicle = new Vehicle(
                "AB12 CDE",
                "Ali",
                "Car"
        );

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        Movement movement1 = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusHours(1),
                savedVehicle
        );

        Movement movement2 = new Movement(
                MovementType.TRIP_END,
                LocalDateTime.now(),
                savedVehicle
        );

        movementRepository.save(movement1);
        movementRepository.save(movement2);

        List<Movement> movements = movementRepository
                .findByVehicleIdOrderByMovementTimeAsc(savedVehicle.getId());

        assertEquals(2, movements.size());
    }

    @Test
    void findTopByVehicleIdOrderByMovementTimeDesc_shouldReturnLatestMovement() {

        Vehicle vehicle = new Vehicle(
                "XY99 ZZZ",
                "Ahmed",
                "Van"
        );

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        Movement movement1 = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusHours(2),
                savedVehicle
        );

        Movement movement2 = new Movement(
                MovementType.TRIP_END,
                LocalDateTime.now(),
                savedVehicle
        );

        movementRepository.save(movement1);
        movementRepository.save(movement2);

        Movement latestMovement = movementRepository
                .findTopByVehicleIdOrderByMovementTimeDesc(savedVehicle.getId())
                .orElse(null);

        assertNotNull(latestMovement);

        assertEquals(
                MovementType.TRIP_END,
                latestMovement.getMovementType()
        );
    }
}