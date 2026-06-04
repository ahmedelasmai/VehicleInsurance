package com.home.vehicleinsurance.misc;


import com.home.vehicleinsurance.controller.AuthController;
import com.home.vehicleinsurance.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Test
    void login_shouldFailForUnknownUser() {

        User user = new User();
        user.setUsername("doesnotexist");
        user.setPassword("123");

        assertThrows(RuntimeException.class, () -> {
            authController.login(user);
        });
    }
}