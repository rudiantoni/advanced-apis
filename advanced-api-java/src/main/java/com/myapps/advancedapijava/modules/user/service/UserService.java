package com.myapps.advancedapijava.modules.user.service;

import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.myapps.advancedapijava.util.StringUtil.strHasNoValue;
import static com.myapps.advancedapijava.util.StringUtil.strHasValue;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User findByEmailOrException(String email) throws HandledException {
    String message = ExceptionType.USER_NOT_FOUND_BY_EMAIL.getMessage().formatted(email);
    return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new HandledException(message, ExceptionType.USER_NOT_FOUND_BY_EMAIL));
  }

  public User findByUsernameOrException(String username) throws HandledException {
    String message = ExceptionType.USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username);
    return repository.findByUsernameIgnoreCase(username).orElseThrow(() -> new HandledException(message, ExceptionType.USER_NOT_FOUND_BY_USERNAME));
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

  public UserRespDto create(UserDto userDto) throws HandledException {
    validateRequiredFields(userDto);
    validateDuplicate(userDto);

    String encodedPassword = CryptUtil.hashSha256(userDto.getPassword());
    userDto.setPassword(encodedPassword);
    User userReceived = UserUtil.toEntityNoId(userDto);
    User userSaved = repository.save(userReceived);
    return UserUtil.toRespDto(userSaved);
  }

  public void validateRequiredFields(UserDto userDto) throws HandledException {
    if (strHasNoValue(userDto.getEmail())) {
      throw new HandledException(ExceptionType.USER_REQUIRED_EMAIL);

    } else if (strHasNoValue(userDto.getUsername())) {
      throw new HandledException(ExceptionType.USER_REQUIRED_USERNAME);

    } else if (strHasNoValue(userDto.getPassword())) {
      throw new HandledException(ExceptionType.USER_REQUIRED_PASSWORD);

    }
  }

  public void validateDuplicate(UserDto userDto) throws HandledException {
    if (strHasValue(userDto.getEmail()) && repository.existsByEmailIgnoreCase(userDto.getEmail())) {
      String message = ExceptionType.USER_EXISTS_BY_EMAIL.getMessage().formatted(userDto.getEmail());
      throw new HandledException(message, ExceptionType.USER_EXISTS_BY_EMAIL);

    } else if (strHasValue(userDto.getUsername()) && repository.existsByUsernameIgnoreCase(userDto.getUsername())) {
      String message = ExceptionType.USER_EXISTS_BY_USERNAME.getMessage().formatted(userDto.getUsername());
      throw new HandledException(message, ExceptionType.USER_EXISTS_BY_USERNAME);

    }
  }

}
