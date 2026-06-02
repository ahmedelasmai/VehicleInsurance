package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.ComplianceViolation;
import org.springframework.stereotype.Component;

@Component
public class SqsViolationNotifier implements ViolationNotifier {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SqsViolationNotifier(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
        this.queueUrl = System.getenv("VIOLATION_QUEUE_URL");
    }

    @Override
    public void notifyViolation(ComplianceViolation violation) {
        String body = String.format(
                "{\"vehicleId\": %d, \"violationType\": \"%s\", \"time\": \"%s\", \"details\": \"%s\"},
                violation.getVehicle().getId(),
                violation.getViolationType().name(),
                violation.getViolationTime(),
                violation.getDetails().replace("\"", "'")
        );

        SendMessageRequest request = sendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(body)
                .build();

        sqsClient.sendMessage(request);
    }
}

