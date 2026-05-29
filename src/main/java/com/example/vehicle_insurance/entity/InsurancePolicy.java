package com.example.vehicle_insurance.entity;


import com.example.vehicle_insurance.enums.PolicyStatus;
import com.example.vehicle_insurance.enums.PolicyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "insurance_policies", indexes = {
   @Index(name = "idx_policy_vehicle", columnList = "vehicle_id"),
   @Index(name = " idx_policy_active", columnList = "vehicle_id, is_active")
})

public class InsurancePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne ( optional = false)
    @JoinColumn(name = "vehicle_id", nullable = false, unique = true)
    private Vehicle vehicle;

    @Column(name = "policy_number", nullable = false)
    private String policyNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_type", nullable = false)
    private PolicyType policyType;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PolicyStatus status;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

       }








