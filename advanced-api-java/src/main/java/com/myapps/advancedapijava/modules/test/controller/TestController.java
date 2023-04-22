package com.myapps.advancedapijava.modules.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
public class TestController {

  @GetMapping
  public ResponseEntity<String> get() {
    return new ResponseEntity<>("Called GET /tests", HttpStatus.OK);
  }

}
