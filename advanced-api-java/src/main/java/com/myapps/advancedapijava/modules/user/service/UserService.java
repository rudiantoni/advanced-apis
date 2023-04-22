package com.myapps.advancedapijava.modules.user.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;
  Logger logger = Util.getLogger(this.getClass());

  public User findByEmailOrException(String email) {
    return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new NoSuchElementException("User with email %s not found.".formatted(email)));
  }

  public User findByUsernameOrException(String username) {
    return repository.findByUsernameIgnoreCase(username).orElseThrow(() -> new NoSuchElementException("User with username %s not found.".formatted(username)));
  }

  public List<User> getAll() {
    return repository.findAll();
  }

}
