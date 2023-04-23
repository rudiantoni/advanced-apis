package com.myapps.advancedapijava.modules.test.controller;

import com.myapps.advancedapijava.modules.auth.service.TokenService;
import com.myapps.advancedapijava.modules.test.dto.TestRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tests")
@RequiredArgsConstructor
public class TestController {
  private final TokenService jwtService;

  @GetMapping
  public ResponseEntity<TestRespDto> get() {
    TestRespDto response = new TestRespDto();
    response.setStatus("CALLED TEST CONTROLLER");

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
