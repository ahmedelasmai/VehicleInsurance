package com.home.vehicleinsurance.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MovementType movementType;

    @Column(nullable = false)
    private LocalDateTime movementTime;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public Movement() {}

    public Movement(MovementType movementType, LocalDateTime movementTime, Vehicle vehicle) {
        this.movementType = movementType;
        this.movementTime = movementTime;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }

    public LocalDateTime getMovementTime() {
        return movementTime;
    }

    public void setMovementTime(LocalDateTime movementTime) {
        this.movementTime = movementTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Corrected logic
    public boolean isAfter(LocalDateTime expiryEndOfDay) {
        return this.movementTime.isAfter(expiryEndOfDay);
    }
}
