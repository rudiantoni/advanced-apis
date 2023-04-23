package com.myapps.advancedapijava.modules.open.controller;

import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.open.service.OpenService;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/open")
public class OpenController {
  private final OpenService service;

  @PostMapping("/auth/login")
  public ResponseEntity<LoginRespDto> authLogin(
    @RequestBody LoginReqDto loginReqDto
  ) {
    return new ResponseEntity<>(service.login(loginReqDto), HttpStatus.CREATED);
  }

  @PostMapping("/users/create")
  public ResponseEntity<UserRespDto> usersCreate(
    @RequestBody UserDto userDto
  ) {
    return new ResponseEntity<>(service.createUser(userDto), HttpStatus.OK);
  }

}
