package com.myapps.apijava.controller;

import com.myapps.apijava.dto.MsgDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open")
public class OpenController {

  @GetMapping("/check")
  public ResponseEntity<MsgDto> getCheck() {
    System.out.println("OPEN CHECK: You reached the open endpoint SUCCESSFULLY");
    return new ResponseEntity<>(new MsgDto("OPEN CHECK: You reached the open endpoint SUCCESSFULLY."), HttpStatus.OK);
  }

}
