package com.myapps.advancedapijava.modules.user.controller;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService service;

  Logger logger = Util.getLogger(this.getClass());

  @GetMapping("")
  public ResponseEntity<List<User>> findAll() {
    logger.info("GET /users");
    return ResponseEntity.ok().body(service.getUsers());
  }

  @PostMapping("")
  public ResponseEntity<User> save(@RequestBody User user) {
    logger.info("POST /users");
    URI uri  = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
    return ResponseEntity.created(uri).body(service.saveUser(user));
  }

}
