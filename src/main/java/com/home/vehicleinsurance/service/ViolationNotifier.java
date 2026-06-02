package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.ComplianceViolation;

public interface ViolationNotifier {
    void notifyViolation(ComplianceViolation violation);
}

