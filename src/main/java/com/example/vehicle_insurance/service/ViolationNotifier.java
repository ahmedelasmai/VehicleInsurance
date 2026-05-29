package com.example.vehicle_insurance.service;

import com.example.vehicle_insurance.entity.ComplianceViolation;

public interface ViolationNotifier {
    void notifyViolation(ComplianceViolation violation);
}
