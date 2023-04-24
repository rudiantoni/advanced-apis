package com.myapps.advancedapijava.modules.auth.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.CryptUtil;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.myapps.advancedapijava.util.StringUtil.*;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  Logger logger = Util.getLogger(this.getClass());

  public LoginRespDto login(LoginReqDto loginReqDto) throws HandledException {
    validateRequiredFields(loginReqDto);

    String sentEncodedPassword = CryptUtil.hashSha256(loginReqDto.getPassword());
    User user = userService.findByEmailOrUsernameOrNull(loginReqDto.getEmail(), loginReqDto.getUsername());
    if (user == null || !Objects.equals(sentEncodedPassword, user.getPassword())) {
      throw new HandledException(ExceptionType.LOGIN_INVALID_LOGIN_OR_PASSWORD);
    }
    String jwtToken = jwtService.generateFullToken(user);

    logger.info("Logged user (id: %s, email: %s, user: %s).".formatted(user.getId(), user.getEmail(), user.getUsername()));

    return LoginRespDto.builder()
      .id(user.getId())
      .email(user.getEmail())
      .username(user.getUsername())
      .token(jwtToken)
      .build();
  }

  public void validateRequiredFields(LoginReqDto loginReqDto) throws HandledException {
    if (strHasNoValue(loginReqDto.getEmail()) && strHasNoValue(loginReqDto.getUsername())) {
      throw new HandledException(ExceptionType.LOGIN_REQUIRED_EMAIL_OR_USERNAME);

    } else if (strHasValue(loginReqDto.getEmail()) && !strIsValidEmail(loginReqDto.getEmail())) {
      throw new HandledException(ExceptionType.EMAIL_FORMAT_INVALID);

    } else if (strHasNoValue(loginReqDto.getPassword())) {
      throw new HandledException(ExceptionType.LOGIN_REQUIRED_PASSWORD);

    }
  }

}
