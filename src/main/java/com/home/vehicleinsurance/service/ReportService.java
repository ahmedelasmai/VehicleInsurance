package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.Movement;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
public class ReportService {

    private final S3Service s3Service;
    private final VehicleRepository vehicleRepository;
    private final MovementRepository movementRepository;

    public ReportService(S3Service s3Service,
                         VehicleRepository vehicleRepository, MovementRepository movementRepository) {
        this.s3Service = s3Service;
        this.vehicleRepository = vehicleRepository;
        this.movementRepository = movementRepository;
    }


    public void generateAndUploadReport() {

        byte[] csv = generateCsv();

        LocalDate date = LocalDate.now();

        s3Service.uploadReport(csv, date);
    }

    public byte[] downloadReport(LocalDate date) {
        return s3Service.downloadReport(date);
    }

    private byte[] generateCsv() {

        List<Vehicle> vehicles = vehicleRepository.findAll();

        StringBuilder sb = new StringBuilder();
        sb.append("id,registration,compliant\n");

        for (Vehicle v : vehicles) {
            sb.append(v.getId()).append(",")
                    .append(v.getRegistrationNumber()).append(",");
//                    .append(v.isCompliant()).append("\n"); waiting on person 2 for this feature
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    

    private byte[] convertToCsv(List<Movement> movements) {

        StringBuilder sb = new StringBuilder();


        sb.append("id,vehicleId,movementType,movementTime\n");


        for (Movement m : movements) {
            sb.append(m.getId()).append(",");
            sb.append(m.getVehicle() != null ? m.getVehicle().getId() : "").append(",");
            sb.append(m.getMovementType()).append(",");
            sb.append(m.getMovementTime()).append("\n");
        }

        return sb.toString().getBytes();
    }

    public byte[] generateMonthlyCsv(YearMonth month) {

        LocalDateTime start = month.atDay(1).atStartOfDay();
        LocalDateTime end = month.atEndOfMonth().atTime(23, 59, 59);

        List<Movement> data = movementRepository
                .findByMovementTimeBetween(start, end);

        return convertToCsv(data);
    }

    public void generateAndUploadMonthly() {

        YearMonth lastMonth = YearMonth.now().minusMonths(1);

        byte[] csv = generateMonthlyCsv(lastMonth);

        s3Service.uploadMonthlyReport(csv, lastMonth);
    }
}