package com.myapps.bavariamunich.controller;

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
@RequestMapping("/closed")
public class ClosedController {
    private static final Logger logger = LoggerFactory.getLogger(ClosedController.class);

    @GetMapping("/check")
    public ResponseEntity<Map<String, String>> readAll() {
        String msg = "CLOSED CHECK: You reached the closed endpoint SUCCESSFULLY.";
        logger.info(msg);
        Map<String, String> result = Collections.singletonMap("message", msg);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
