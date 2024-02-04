package com.myapps.apijava.service;

import com.myapps.apijava.entity.User;
import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.exception.HandledException;
import com.myapps.apijava.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User findActiveByEmailOrException(String email) throws HandledException {
    return Optional.ofNullable(repository.findActiveByEmail(email))
      .orElseThrow(() -> new HandledException(ExceptionType.INVALID_EMAIL_OR_PASSWORD));
  }

  public Boolean existsByEmail(String email) {
    return repository.existsByEmail(email);
  }

  public User save(User user) {
    return repository.save(user);
  }
}
