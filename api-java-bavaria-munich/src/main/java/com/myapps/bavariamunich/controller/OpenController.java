package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.service.PasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/open")
public class OpenController {

    private final PasswordService passwordService;

    public OpenController(
            PasswordService passwordService
    ){
        this.passwordService = passwordService;
    }

    private static final Logger logger = LoggerFactory.getLogger(OpenController.class);

    @GetMapping("/check")
    public ResponseEntity<Map<String, String>> readAll() {
        String rawPass = "somePass123";
        String hashedPass = "$2a$10$tOgpcQdZReuXRKL750k8Z.MhDshRWfaJHxHNkTz30In8dAqWW.BvK";
        boolean match = passwordService.matches(rawPass, hashedPass);
        logger.info(String.format("Matches: (%s)", match));
        // Raw password: (somePass123)
        // Using API: ($2a$10$tOgpcQdZReuXRKL750k8Z.MhDshRWfaJHxHNkTz30In8dAqWW.BvK)
        // Using script: ($2a$10$qGjxraqbAY4zho599c8dBO8Dpo8EfBo64HV.hM08tvNuHIlvzSkD.)

        String msg = "OPEN CHECK: You reached the open endpoint SUCCESSFULLY.";
        logger.info(msg);
        Map<String, String> result = Collections.singletonMap("message", msg);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
