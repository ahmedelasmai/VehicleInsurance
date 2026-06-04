package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

@Service
public class ReportService {

    private final S3Service s3Service;
    private final VehicleRepository vehicleRepository;

    public ReportService(S3Service s3Service,
                         VehicleRepository vehicleRepository) {
        this.s3Service = s3Service;
        this.vehicleRepository = vehicleRepository;
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
}