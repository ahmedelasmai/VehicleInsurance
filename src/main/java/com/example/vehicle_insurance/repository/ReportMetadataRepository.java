package com.example.vehicle_insurance.repository;


import com.example.vehicle_insurance.entity.ReportMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportMetadataRepository extends JpaRepository<ReportMetadata, Long> {
    Optional<ReportMetadata> findByReportDate(LocalDate reportDate);
}
