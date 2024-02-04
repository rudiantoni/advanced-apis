package com.myapps.apijava.controller;

import com.myapps.apijava.dto.CreateUserReqDto;
import com.myapps.apijava.dto.LoginReqDto;
import com.myapps.apijava.dto.LoginRespDto;
import com.myapps.apijava.entity.User;
import com.myapps.apijava.exception.HandledException;
import com.myapps.apijava.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/login")
  public ResponseEntity<LoginRespDto> loginUser(
    @RequestBody LoginReqDto loginReqDto
  ) throws HandledException {
    LoginRespDto result = service.login(loginReqDto);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<User> createUser(
    @RequestBody CreateUserReqDto createUserReqDto
  ) throws HandledException {
    User result = service.create(createUserReqDto);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
