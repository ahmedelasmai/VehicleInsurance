package com.home.vehicleinsurance.repository;

import com.home.vehicleinsurance.entity.ReportMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportMetadataRepository extends JpaRepository<ReportMetadata, Long> {
    Optional<ReportMetadata> findByReportDate(LocalDate reportDate);
}
