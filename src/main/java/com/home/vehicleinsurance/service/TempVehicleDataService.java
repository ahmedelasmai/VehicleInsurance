package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.util.List;
// 
@Service
public class TempVehicleDataService {

    public List<Vehicle> getVehicles() {
        return List.of(
                new Vehicle(1L, "ABC123", "Ahmed", "Car"),
                new Vehicle(2L, "XYZ789", "John", "Truck")
        );
    }
}