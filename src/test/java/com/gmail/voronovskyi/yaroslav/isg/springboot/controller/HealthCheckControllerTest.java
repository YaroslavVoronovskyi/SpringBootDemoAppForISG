package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthCheckControllerTest {

    private static final String HEALTH_CHECK_MESSAGE = "I'm alive!!";

    private final HealthCheckController healthCheckController = new HealthCheckController();

    @Test
    void healthCheck() {
        assertEquals(HEALTH_CHECK_MESSAGE, healthCheckController.healthCheck());
    }
}