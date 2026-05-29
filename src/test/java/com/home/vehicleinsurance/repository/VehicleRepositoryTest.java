package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void existsByRegistrationNumber_shouldReturnTrue() {

        String reg = "REG-" + UUID.randomUUID();

        Vehicle vehicle = new Vehicle(
                reg,
                "Ali",
                "Car"
        );

        vehicleRepository.save(vehicle);

        boolean exists = vehicleRepository.existsByRegistrationNumber(reg);

        assertTrue(exists);
    }

    @Test
    void findByRegistrationNumber_shouldReturnVehicle() {

        String reg = "REG-" + UUID.randomUUID();

        Vehicle vehicle = new Vehicle(
                reg,
                "Ahmed",
                "Van"
        );

        vehicleRepository.save(vehicle);

        Vehicle foundVehicle = vehicleRepository
                .findByRegistrationNumber(reg)
                .orElse(null);

        assertNotNull(foundVehicle);
        assertEquals("Ahmed", foundVehicle.getOwnerName());
    }
}