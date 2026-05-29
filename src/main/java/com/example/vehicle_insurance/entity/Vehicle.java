package com.example.vehicle_insurance.entity;

import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String registrationNumber;

    public Vehicle() {}
    public Vehicle(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public Long getId() {
        return id;
    }

    public String getRegistrationNumber() {return registrationNumber;}

}
