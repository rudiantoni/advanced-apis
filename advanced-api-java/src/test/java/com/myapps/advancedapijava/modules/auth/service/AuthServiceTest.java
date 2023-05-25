package com.myapps.advancedapijava.modules.auth.service;

import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
  @Mock
  private UserRepository repository;
  private AuthService underTest;
  @BeforeEach
  void setUp() {
    UserService userService = new UserService(repository);
    JwtService jwtService = new JwtService();
    underTest = new AuthService(userService, jwtService);
    AppProperties.securityTokenExpirationHours = 24;
    AppProperties.securitySecretKey = "28472B4B6150645367566B5970337336763979244226452948404D6351655468";
    AppProperties.securityTokenPrefix = "Bearer";
  }

  @Test
  @DisplayName("Given an user name and an email, when it makes login, it should login.")
  void login() throws HandledException {
    // Given
    String email = "user.test@email.com";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    LoginReqDto loginInfo = LoginReqDto.builder().email(email).password(pass).build();
    User user = User.builder().id(1L).email(email).username("user.test").password(encPass).build();
    Optional<User> optionalUser = Optional.of(user);
    // When
    when(repository.findByEmailIgnoreCase(any(String.class))).thenReturn(optionalUser);
    LoginRespDto loginResp = underTest.login(loginInfo);
    // Then
    var x = true;


  }

  @Test
  void validateRequiredFields() {
  }
}