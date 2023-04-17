package com.myapps.advancedapijava.modules.product.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.util.Util;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-products")
public class TestProductController {
  Logger logger = Util.getLogger(this.getClass());

  @GetMapping("")
  private ResponseEntity<String> test() {
    logger.info("GET /test-products");
    return new ResponseEntity<>("Called test() successfully", HttpStatus.OK);
  }

}
