package com.home.vehicleinsurance.integration;


public interface SqsNotificationService {
        void sendViolationMessage(String message);
    }

