package com.myapps.advancedapijava.modules.user.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.product.entity.Product;
import com.myapps.advancedapijava.modules.product.util.ProductUtil;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
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

  public UserRespDto createResp(UserDto userDto) {
    validateRequiredFields(userDto);
    validateDuplicate(userDto);
    User userReceived = UserUtil.toEntityNoId(userDto);
    User userSaved = repository.save(userReceived);
    return UserUtil.toRespDto(userSaved);
  }

  public void validateRequiredFields(UserDto userDto) {
    if (userDto.getEmail() == null) {
      throw new IllegalArgumentException("User email is required.");
    } else if (userDto.getUsername() == null) {
      throw new IllegalArgumentException("User username is required.");
    } else if (userDto.getPassword() == null) {
      throw new IllegalArgumentException("User password is required.");
    }
  }

  public void validateDuplicate(UserDto userDto) {
    if (userDto.getEmail() != null && repository.existsByEmailIgnoreCase(userDto.getEmail())) {
      throw new IllegalArgumentException("User with email %s already exists.".formatted(userDto.getEmail()));
    } else if (userDto.getUsername() != null && repository.existsByUsernameIgnoreCase(userDto.getUsername())) {
      throw new IllegalArgumentException("User with username %s already exists.".formatted(userDto.getUsername()));
    }
  }

}
