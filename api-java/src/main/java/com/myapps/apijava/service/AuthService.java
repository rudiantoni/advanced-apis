package com.myapps.apijava.service;

import com.myapps.apijava.auth.JwtService;
import com.myapps.apijava.dto.LoginReqDto;
import com.myapps.apijava.dto.LoginRespDto;
import com.myapps.apijava.dto.UserSecureDto;
import com.myapps.apijava.entity.User;
import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.exception.HandledException;
import com.myapps.apijava.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserService userService;
  private final JwtService jwtService;
  public LoginRespDto login(LoginReqDto loginReqDto) throws HandledException {
    Boolean hasEmail = !ValidationUtil.isNullOrBlank(loginReqDto.getEmail());
    Boolean hasPassword = !ValidationUtil.isNullOrBlank(loginReqDto.getPassword());

    if (hasEmail && hasPassword) {
      User user = userService.findActiveByEmailAndPasswordOrException(loginReqDto.getEmail(), loginReqDto.getPassword());
      if (user.getPassword().equals(loginReqDto.getPassword())) {
        String jwtToken = jwtService.createToken(user);
        UserSecureDto userSecureDto = user.toSecureDto();
        return LoginRespDto.builder()
          .user(userSecureDto)
          .token(jwtToken)
          .build();
      }
    }
    throw new HandledException(ExceptionType.INVALID_EMAIL_OR_PASSWORD);
  }
}
