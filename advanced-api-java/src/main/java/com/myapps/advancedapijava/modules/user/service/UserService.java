package com.myapps.advancedapijava.modules.user.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;
  Logger logger = Util.getLogger(this.getClass());

  public User saveUser(User user) {
    logger.info("Saving new user to the database: %s, %s.".formatted(user.getEmail(), user.getUsername()));
    return repository.save(user);
  }

  public User getUserByEmail(String email) {
    logger.info("Fetching user: %s.".formatted(email));
    return repository.findByEmail(email);
  }

  public User getUserByUsername(String username) {
    logger.info("Fetching user: %s.".formatted(username));
    return repository.findByUsername(username);
  }

  public List<User> getUsers() {
    logger.info("Fetching all users.");
    return repository.findAll();
  }
}
