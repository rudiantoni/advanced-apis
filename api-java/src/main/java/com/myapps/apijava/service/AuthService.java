package com.myapps.apijava.service;

import com.myapps.apijava.auth.JwtService;
import com.myapps.apijava.dto.CreateUserReqDto;
import com.myapps.apijava.dto.LoginReqDto;
import com.myapps.apijava.dto.LoginRespDto;
import com.myapps.apijava.dto.UserSecureDto;
import com.myapps.apijava.entity.User;
import com.myapps.apijava.exception.HandledException;
import com.myapps.apijava.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.myapps.apijava.enums.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final BCryptPasswordEncoder bCrypt;
  private final UserService userService;
  private final JwtService jwtService;

  public LoginRespDto login(LoginReqDto loginReqDto) throws HandledException {
    validateLoginReqDto(loginReqDto);
    User user = userService.findActiveByEmailOrException(loginReqDto.getEmail());
    if (bCryptMatches(loginReqDto.getPassword(), user.getPassword())) {
      String jwtToken = jwtService.createToken(user);
      UserSecureDto userSecureDto = user.toSecureDto();
      return LoginRespDto.builder()
        .user(userSecureDto)
        .token(jwtToken)
        .build();
    } else {
      throw new HandledException(INVALID_EMAIL_OR_PASSWORD);
    }
  }

  public User create(CreateUserReqDto createUserReqDto) throws HandledException {
    validateUserCreateReqDto(createUserReqDto);
    User newUser = User.builder()
      .email(createUserReqDto.getEmail())
      .username(createUserReqDto.getUsername())
      .password(bCryptEncode(createUserReqDto.getPassword()))
      .active(true)
      .build();
    return userService.save(newUser);
  }

  private void validateLoginReqDto(LoginReqDto loginReqDto) throws HandledException {
    if (ValidationUtil.isNullOrBlank(loginReqDto.getEmail())) {
      throw new HandledException(INVALID_EMAIL_OR_PASSWORD);
    } else if (ValidationUtil.isNullOrBlank(loginReqDto.getPassword())) {
      throw new HandledException(INVALID_EMAIL_OR_PASSWORD);
    }
  }

  private void validateUserCreateReqDto(CreateUserReqDto createUserReqDto) throws HandledException {
    if (ValidationUtil.isNullOrBlank(createUserReqDto.getEmail())) {
      throw new HandledException(REQUIRED_FIELD_IS_NULL_OR_BLANK, REQUIRED_FIELD_IS_NULL_OR_BLANK.getMessage().formatted("email"));
    } else if (ValidationUtil.isNullOrBlank(createUserReqDto.getUsername())) {
      throw new HandledException(REQUIRED_FIELD_IS_NULL_OR_BLANK, REQUIRED_FIELD_IS_NULL_OR_BLANK.getMessage().formatted("username"));
    } else if (ValidationUtil.isNullOrBlank(createUserReqDto.getPassword())) {
      throw new HandledException(REQUIRED_FIELD_IS_NULL_OR_BLANK, REQUIRED_FIELD_IS_NULL_OR_BLANK.getMessage().formatted("password"));
    } else if (!ValidationUtil.isValidEmail(createUserReqDto.getEmail())) {
      throw new HandledException(FIELD_FORMAT_INVALID, FIELD_FORMAT_INVALID.getMessage().formatted("email", "a malformed email"));
    } else if (createUserReqDto.getEmail().length() > 255) {
      throw new HandledException(FIELD_FORMAT_INVALID, FIELD_FORMAT_INVALID.getMessage().formatted("email", "more than 255 characters"));
    } else if (createUserReqDto.getUsername().length() > 255) {
      throw new HandledException(FIELD_FORMAT_INVALID, FIELD_FORMAT_INVALID.getMessage().formatted("username", "more than 255 characters"));
    } else if (createUserReqDto.getPassword().length() > 64) {
      throw new HandledException(FIELD_FORMAT_INVALID, FIELD_FORMAT_INVALID.getMessage().formatted("password", "more than 64 characters"));
    } else if (userService.existsByEmail(createUserReqDto.getEmail())) {
      throw new HandledException(REGISTER_WITH_FIELD_ALREADY_EXISTS, REGISTER_WITH_FIELD_ALREADY_EXISTS.getMessage().formatted("user", "email"));
    }
  }

  private String bCryptEncode(String str) {
    return bCrypt.encode(str);
  }

  private Boolean bCryptMatches(String rawStr, String encodedStr) {
    return bCrypt.matches(rawStr, encodedStr);
  }
}
