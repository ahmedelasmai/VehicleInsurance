package com.example.vehicle_insurance.exception;

public class ResourceNotFoundException  extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
