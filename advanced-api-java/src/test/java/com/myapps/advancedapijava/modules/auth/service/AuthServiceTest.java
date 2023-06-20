package com.myapps.advancedapijava.modules.auth.service;

import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.auth.dto.LoginReqDto;
import com.myapps.advancedapijava.modules.auth.dto.LoginRespDto;
import com.myapps.advancedapijava.modules.auth.model.Token;
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

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
  @Mock
  private UserRepository repository;
  private JwtService jwtService;
  private AuthService underTest;
  @BeforeEach
  void setUp() {
    UserService userService = new UserService(repository);
    JwtService jwtService = new JwtService();
    this.jwtService = jwtService;
    underTest = new AuthService(userService, jwtService);
    AppProperties.securityTokenExpirationHours = 24;
    AppProperties.securitySecretKey = "28472B4B6150645367566B5970337336763979244226452948404D6351655468";
    AppProperties.securityTokenPrefix = "Bearer";
  }

  @Test
  @DisplayName("Given an user name or an email, when it makes login, it should login.")
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
  @DisplayName("Given an wrong user name and an email, when it makes login, it will throw an exception.")
  void willThrowLoginInvalid() {
    // Given
    String email = "user.test@email.com";
    String pass = "user";
    LoginReqDto loginInfo = LoginReqDto.builder().email(email).password(pass).build();
    given(repository.findByEmailIgnoreCase(any(String.class))).willReturn(Optional.empty());
    // When
    // Then
    String message = ExceptionType.LOGIN_INVALID_LOGIN_OR_PASSWORD.getMessage();
    assertThatThrownBy(() -> underTest.login(loginInfo))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(message);

  }

  @DisplayName("Given an user name or an email with an wrong password, when it makes login, it will throw an exception.")
  @Test
  void willThrowExceptionOnWrongPassword() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String passWrong = "userPass";
    String encPass = CryptUtil.hashSha256(pass);
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    Optional<User> optionalUser = Optional.of(user);
    LoginReqDto loginInfo = LoginReqDto.builder().email(email).password(passWrong).build();
    String msgInvalidLogin = ExceptionType.LOGIN_INVALID_LOGIN_OR_PASSWORD.getMessage();

    // When
    when(repository.findByEmailIgnoreCase(any(String.class))).thenReturn(optionalUser);

    // Then
    assertThatThrownBy(() -> underTest.login(loginInfo))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(msgInvalidLogin);
  }

  @DisplayName("Given an null email and a null user name, when it makes login, it will throw an exception.")
  @Test
  void willThrowExceptionOnEmailNullAndUsernameNull() {
  // Given
    String pass = "user";
    LoginReqDto loginInfo = LoginReqDto.builder().email(null).username(null).password(pass).build();
    String messageLoginRequired = ExceptionType.LOGIN_REQUIRED_EMAIL_OR_USERNAME.getMessage();
    // When
    // Then
    assertThatThrownBy(() -> underTest.login(loginInfo))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(messageLoginRequired);
  }

  @DisplayName("Given an null email with user name, when it makes login, it will not throw an exception.")
  @Test
  void willNotThrowExceptionOnEmailNullWithUsername() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    LoginReqDto loginInfo = LoginReqDto.builder().email(null).username(username).password(pass).build();
    String encPass = CryptUtil.hashSha256(pass);
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    Optional<User> optionalUser = Optional.of(user);

    // When
    when(repository.findByUsernameIgnoreCase(any(String.class))).thenReturn(optionalUser);

    // Then
    assertThatNoException().isThrownBy(() -> underTest.login(loginInfo));

  }

  @DisplayName("Given an invalid email format, when it makes login, it will throw an exception.")
  @Test
  void willThrowExceptionOnEmailInvalid() {
    // Given
    String emailNonFormat = "user.testNNNemail.com";
    String username = "user.test";
    String pass = "pass";
    LoginReqDto loginInfo = LoginReqDto.builder().email(emailNonFormat).username(username).password(pass).build();
    String msgEmailInvalid = ExceptionType.EMAIL_FORMAT_INVALID.getMessage();

    // When
    // Then
    assertThatThrownBy(() -> underTest.login(loginInfo))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(msgEmailInvalid);

  }

  @Test
  @DisplayName("Given an email with username and without a password, when it makes login, it will throw an exception.")
  void willThrowExceptionOnPasswordInvalid() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    LoginReqDto loginInfo = LoginReqDto.builder().email(email).username(username).password(null).build();
    String msgRequiredPassword = ExceptionType.LOGIN_REQUIRED_PASSWORD.getMessage();
    // When
    // Then
    assertThatThrownBy(() -> underTest.login(loginInfo))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(msgRequiredPassword);

  }

}