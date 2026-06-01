package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
// 
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}