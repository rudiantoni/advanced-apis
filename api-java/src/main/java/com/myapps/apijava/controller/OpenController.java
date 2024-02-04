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
@RequiredArgsConstructor
public class OpenController {

  @GetMapping("/check")
  public ResponseEntity<?> getCheck() {
    try {
      System.out.println("OPEN CHECK: You reached the open endpoint SUCCESSFULLY");
      return new ResponseEntity<>(new MsgDto("OPEN CHECK: You reached the open endpoint SUCCESSFULLY."), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(new MsgDto("INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
