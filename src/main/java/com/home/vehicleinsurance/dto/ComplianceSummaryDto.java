package com.home.vehicleinsurance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComplianceSummaryDto {

    @JsonProperty("totalVehicles")
    private long totalVehicles;

    @JsonProperty("violatingVehicles")
    private long violatingVehicles;

    public ComplianceSummaryDto() {}

    public ComplianceSummaryDto(long totalVehicles, long violatingVehicles) {
        this.totalVehicles = totalVehicles;
        this.violatingVehicles = violatingVehicles;
    }

    public long getTotalVehicles() {
        return totalVehicles;
    }

    public void setTotalVehicles(long totalVehicles) {
        this.totalVehicles = totalVehicles;
    }

    public long getViolatingVehicles() {
        return violatingVehicles;
    }

    public void setViolatingVehicles(long violatingVehicles) {
        this.violatingVehicles = violatingVehicles;
    }
}
