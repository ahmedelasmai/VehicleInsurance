package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.entity.Movement;
import com.home.vehicleinsurance.service.MovementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
public class MovementController {

    private final MovementService movementService;

    public MovementController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public Movement createMovement(@RequestBody Movement movement) {
        return movementService.createMovement(movement);
    }

    @GetMapping("/{vehicleId}")
    public List<Movement> getMovementsByVehicleId(@PathVariable Long vehicleId) {
        return movementService.getMovementsByVehicleId(vehicleId);
    }
}