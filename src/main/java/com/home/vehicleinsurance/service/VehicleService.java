package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import com.home.vehicleinsurance.entity.Vehicle;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        if (vehicleRepository.existsByRegistrationNumber(vehicle.getRegistrationNumber())) {
            throw new RuntimeException("Registration number already exists");
        }

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
        existingVehicle.setOwnerName(updatedVehicle.getOwnerName());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {

        Vehicle vehicle = getVehicleById(id);

        vehicleRepository.delete(vehicle);
    }

}