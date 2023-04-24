package com.myapps.advancedapijava.modules.open.service;

import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.auth.service.AuthService;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import com.myapps.advancedapijava.modules.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenService {
  private final AuthService authService;
  private final UserService userService;

  public LoginRespDto login(LoginReqDto loginReqDto) throws HandledException {
    return authService.login(loginReqDto);
  }

  public UserRespDto createUser(UserDto userDto) throws HandledException {
    return userService.create(userDto);
  }

}
