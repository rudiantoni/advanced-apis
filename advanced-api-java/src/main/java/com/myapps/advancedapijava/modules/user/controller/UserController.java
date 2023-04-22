package com.myapps.advancedapijava.modules.user.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
  private final UserService service;
  Logger logger = Util.getLogger(this.getClass());

  @GetMapping("")
  public ResponseEntity<List<User>> findAll() {
    logger.info("GET /users");
    return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }

}
