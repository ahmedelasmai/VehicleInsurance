package com.example.vehicle_insurance.entity;


import com.example.vehicle_insurance.enums.ViolationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "compliance_violations")
public class ComplianceViolation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column( nullable = false)
    private ViolationType violationType;

    @Column( nullable = false)
    private LocalDateTime violationTime;

    @Column( nullable = false, length = 150)
    private String details;

    @Column( nullable = false)
    private boolean resolved = false;

}

