package com.myapps.apijava.controller;

import com.myapps.apijava.auth.AuthenticationToken;
import com.myapps.apijava.dto.MsgDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/closed")
public class ClosedController {

  @GetMapping("/check")
  public ResponseEntity<?> getCheck(
    AuthenticationToken authentication
  ) {
    try {
      System.out.println("CLOSED CHECK: You reached the closed endpoint SUCCESSFULLY");
      return new ResponseEntity<>(new MsgDto("CLOSED CHECK: You reached the closed endpoint SUCCESSFULLY."), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(new MsgDto("INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
