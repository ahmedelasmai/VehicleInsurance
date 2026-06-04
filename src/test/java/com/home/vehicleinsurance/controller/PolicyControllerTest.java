package com.home.vehicleinsurance.controller;


import com.home.vehicleinsurance.entity.Vehicle;
import com.home.vehicleinsurance.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PolicyControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    void createPolicy_returnsCreated() throws Exception {
        Vehicle v = new Vehicle();
        v.setRegistrationNumber("REG600");
        v.setOwnerName("ControllerOwner");
        v.setVehicleType("CAR");
        vehicleRepository.save(v);

        mvc.perform(post("/api/policies/" + v.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "policyNumber": "POL600",
                                  "policyType": "COMPREHENSIVE",
                                  "issueDate": "2024-01-01",
                                  "expiryDate": "2024-12-31"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.policyNumber").value("POL600"));
    }
}
