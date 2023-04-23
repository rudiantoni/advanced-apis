package com.myapps.advancedapijava.modules.user.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.myapps.advancedapijava.util.StringUtil.strHasNoValue;
import static com.myapps.advancedapijava.util.StringUtil.strHasValue;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User findByEmailOrException(String email) {
    return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new NoSuchElementException("User with email %s not found.".formatted(email)));
  }

  public User findByUsernameOrException(String username) {
    return repository.findByUsernameIgnoreCase(username).orElseThrow(() -> new NoSuchElementException("User with username %s not found.".formatted(username)));
  }

  public User findByEmailOrUsernameOrNull(String email, String username) {
    if (email != null && !email.isBlank()) {
      Optional<User> userByEmail = repository.findByEmailIgnoreCase(email);
      if (userByEmail.isPresent()) {
        return userByEmail.get();
      }

    } else if (username != null && !username.isBlank()) {
      Optional<User> userByUsername = repository.findByUsernameIgnoreCase(username);
      if (userByUsername.isPresent()) {
        return userByUsername.get();
      }

    }
    return null;
  }

  public List<User> findAll() {
    return repository.findAll();
  }

  public UserRespDto create(UserDto userDto) {
    validateRequiredFields(userDto);
    validateDuplicate(userDto);

    String encodedPassword = CryptUtil.hashSha256(userDto.getPassword());
    userDto.setPassword(encodedPassword);
    User userReceived = UserUtil.toEntityNoId(userDto);
    User userSaved = repository.save(userReceived);
    return UserUtil.toRespDto(userSaved);
  }

  public void validateRequiredFields(UserDto userDto) {
    if (strHasNoValue(userDto.getEmail())) {
      throw new IllegalArgumentException("User email is required.");

    } else if (strHasNoValue(userDto.getUsername())) {
      throw new IllegalArgumentException("User username is required.");

    } else if (strHasNoValue(userDto.getPassword())) {
      throw new IllegalArgumentException("User password is required.");

    }
  }

  public void validateDuplicate(UserDto userDto) {
    if (strHasValue(userDto.getEmail()) && repository.existsByEmailIgnoreCase(userDto.getEmail())) {
      throw new IllegalArgumentException("User with email %s already exists.".formatted(userDto.getEmail()));

    } else if (strHasValue(userDto.getUsername()) && repository.existsByUsernameIgnoreCase(userDto.getUsername())) {
      throw new IllegalArgumentException("User with username %s already exists.".formatted(userDto.getUsername()));

    }
  }

}
