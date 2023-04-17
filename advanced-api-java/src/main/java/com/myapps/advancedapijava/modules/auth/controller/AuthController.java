package com.myapps.advancedapijava.modules.auth.controller;

import com.myapps.advancedapijava.modules.auth.dto.AuthReqDto;
import com.myapps.advancedapijava.modules.auth.dto.AuthResDto;
import com.myapps.advancedapijava.modules.auth.dto.RegisterReqDto;
import com.myapps.advancedapijava.modules.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService service;

  @PostMapping("/register")
  public ResponseEntity<AuthResDto> register(
    @RequestBody RegisterReqDto request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthResDto> authenticate(
    @RequestBody AuthReqDto request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

}
