package com.home.vehicleinsurance.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.home.vehicleinsurance.entity.ViolationType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViolationDto {

    private Long vehicleId;
    private String registrationNumber;
    private String ownerName;

    private ViolationType violationType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate policyExpiry;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTripStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTripEnd;

    public ViolationDto() {}

    public ViolationDto(Long vehicleId,
                        String registrationNumber,
                        String ownerName,
                        ViolationType violationType,
                        LocalDate policyExpiry,
                        LocalDateTime lastTripStart,
                        LocalDateTime lastTripEnd) {
        this.vehicleId = vehicleId;
        this.registrationNumber = registrationNumber;
        this.ownerName = ownerName;
        this.violationType = violationType;
        this.policyExpiry = policyExpiry;
        this.lastTripStart = lastTripStart;
        this.lastTripEnd = lastTripEnd;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public LocalDate getPolicyExpiry() {
        return policyExpiry;
    }

    public LocalDateTime getLastTripStart() {
        return lastTripStart;
    }

    public LocalDateTime getLastTripEnd() {
        return lastTripEnd;
    }
}
