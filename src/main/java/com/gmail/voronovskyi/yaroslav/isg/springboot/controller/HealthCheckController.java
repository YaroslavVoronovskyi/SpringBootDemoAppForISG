package com.gmail.voronovskyi.yaroslav.isg.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HealthCheckController {

    @GetMapping("/healthcheck")
    public String healthCheck() {
        log.info("I'm alive!!");
        return "I'm alive!!";
    }
}
