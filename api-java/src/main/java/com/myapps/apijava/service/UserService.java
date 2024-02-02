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

  public User findActiveByEmailAndPasswordOrException(String email, String password) throws HandledException {
    return Optional.ofNullable(repository.findActiveByEmailAndPassword(email, password))
      .orElseThrow(() -> new HandledException(ExceptionType.INVALID_EMAIL_OR_PASSWORD));
  }
}
