package com.myapps.advancedapijava.modules.open.service;

import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.auth.model.Token;
import com.myapps.advancedapijava.modules.auth.service.AuthService;
import com.myapps.advancedapijava.modules.auth.service.JwtService;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpenServiceTest {
  @Mock
  private UserRepository repository;
  private OpenService underTest;
  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService();
    UserService userService = new UserService(repository);
    AuthService authService = new AuthService(userService, jwtService);
    underTest = new OpenService(authService, userService);
    AppProperties.securityTokenExpirationHours = 24;
    AppProperties.securitySecretKey = "28472B4B6150645367566B5970337336763979244226452948404D6351655468";
    AppProperties.securityTokenPrefix = "Bearer";
  }

  @Test
  @DisplayName("Given an user login info, when it makes login, it should login.")
  void willLoginByUserOrEmail() throws HandledException {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    LoginReqDto loginInfo = LoginReqDto.builder().email(email).password(pass).build();
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    Optional<User> optionalUser = Optional.of(user);

    // When
    when(repository.findByEmailIgnoreCase(any(String.class))).thenReturn(optionalUser);
    LoginRespDto loginResp = underTest.login(loginInfo);
    String jwtToken = loginResp.getToken().substring(AppProperties.securityTokenPrefix.length() + 1);
    Token decodedToken = jwtService.decodeToken(jwtToken);

    // Then
    assertThat(decodedToken.getEmail()).isEqualTo(email);
    assertThat(decodedToken.getUsername()).isEqualTo(username);
  }

  @Test
  @DisplayName("Given username, when it creates a user, it must exist.")
  void createUser() throws HandledException {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    UserDto userDto = UserDto.builder().email(email).username(username).password(pass).build();
    User user = User.builder().id(null).email(email).username(username).password(encPass).build();

    // When
    when(repository.save(any(User.class))).thenReturn(user);
    underTest.createUser(userDto);

    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(repository).save(userArgumentCaptor.capture());
    User capturedUser = userArgumentCaptor.getValue();
    assertThat(capturedUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);

    assertThat(userDto).isNotEqualTo(user);
  }




}