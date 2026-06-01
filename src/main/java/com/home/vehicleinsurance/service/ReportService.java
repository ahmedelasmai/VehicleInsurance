package com.home.vehicleinsurance.service;

import com.home.vehicleinsurance.entity.Vehicle;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ReportService {

    public byte[] generateCsv(List<Vehicle> vehicles) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);

            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "Registration", "Owner", "Type")
                    .build();

            try (CSVPrinter csvPrinter = new CSVPrinter(writer, format)) {
                for (Vehicle v : vehicles) {
                    csvPrinter.printRecord(
                            v.getId(),
                            v.getRegistrationNumber(),
                            v.getOwnerName(),
                            v.getVehicleType()
                    );
                }

                csvPrinter.flush();
            }

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("CSV generation failed", e);
        }
    }
}