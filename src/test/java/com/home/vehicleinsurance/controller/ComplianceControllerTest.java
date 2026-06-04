package com.home.vehicleinsurance.controller;

import com.home.vehicleinsurance.entity.Movement;
import com.home.vehicleinsurance.entity.MovementType;
import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.MovementRepository;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;





import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest

class ComplianceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Test
    @WithMockUser(roles = "USER")
    void getViolations_returnsList() throws Exception {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG700");
        v.setOwnerName("ControllerTest");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        Movement start = new Movement(
                MovementType.TRIP_START,
                LocalDateTime.now().minusDays(1),
                v
        );
        movementRepository.save(start);

        mvc.perform(get("/api/compliance/violations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].registrationNumber").value("REG700"));
    }
}
