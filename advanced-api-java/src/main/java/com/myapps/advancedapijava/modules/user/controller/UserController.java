package com.myapps.advancedapijava.modules.user.controller;

import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<UserRespDto> create(
    @RequestBody UserDto userDto
  ) throws HandledException {
    return new ResponseEntity<>(service.create(userDto), HttpStatus.OK);
  }

}
