package com.example.vehicle_insurance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "report_metadata")
public class ReportMetadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;
    private String month;

    private int totalVehicles;
    private int compliantVehicles;
    private int violatingVehicles;

    @Column (nullable = false)
    private String s3ObjectKey;

    private LocalDateTime generatedAt;
}
