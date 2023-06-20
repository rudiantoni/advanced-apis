package com.myapps.advancedapijava.modules.auth.service;

import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.modules.auth.model.Token;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.myapps.advancedapijava.util.DateUtil;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtServiceTest {
  private JwtService underTest;
  @BeforeEach
  void setUp() {
    underTest = new JwtService();
    AppProperties.securityTokenExpirationHours = 24;
    AppProperties.securitySecretKey = "28472B4B6150645367566B5970337336763979244226452948404D6351655468";
    AppProperties.securityTokenPrefix = "Bearer";
  }

  @Test
  @DisplayName("Given a token, it should be expired.")
  void isTokenExpired() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    Date iat = new Date(1682899200000L); // May 1, 2023 00:00:00
    Date exp = new Date(1685577600000L); // Jun 1, 2023 00:00:00
    Token token = Token.builder().email(email).username(username).issuedAtDate(iat).expirationDate(exp).build();

    // When
    Boolean isExpired = underTest.isTokenExpired(token);

    // Then
    assertThat(isExpired).isTrue();
  }

  @Test
  @DisplayName("Given an expired token, it should be invalid.")
  void isTokenValidWhenExpired() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    Date now = new Date();
    Date iat = DateUtil.minusHours(now, 48);
    Date exp = DateUtil.minusHours(now, 24);
    Token token = Token.builder().email(email).username(username).issuedAtDate(iat).expirationDate(exp).build();
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    // When
    Boolean isValid = underTest.isTokenValid(user, token);

    // Then
    assertThat(isValid).isFalse();
  }

  @Test
  @DisplayName("Given a non expired token, it should be valid.")
  void isTokenValid() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    Date now = new Date();
    Date iat = DateUtil.minusHours(now, 24);
    Date exp = DateUtil.plusHours(now, 24);
    Token token = Token.builder().email(email).username(username).issuedAtDate(iat).expirationDate(exp).build();
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    // When
    Boolean isValid = underTest.isTokenValid(user, token);

    // Then
    assertThat(isValid).isTrue();
  }

  @Test
  @DisplayName("Given a token with a different email from the user, it should be invalid.")
  void isTokenValidWhenDifferentEmail() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    Date now = new Date();
    Date iat = DateUtil.minusHours(now, 24);
    Date exp = DateUtil.plusHours(now, 24);
    Token token = Token.builder().email("diffmail").username(username).issuedAtDate(iat).expirationDate(exp).build();
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    // When
    Boolean isValid = underTest.isTokenValid(user, token);

    // Then
    assertThat(isValid).isFalse();
  }


  @Test
  @DisplayName("Given a token with a different username from the user, it should be invalid.")
  void isTokenValidWhenDifferentUsername() {
    // Given
    String email = "user.test@email.com";
    String username = "user.test";
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    Date now = new Date();
    Date iat = DateUtil.minusHours(now, 24);
    Date exp = DateUtil.plusHours(now, 24);
    Token token = Token.builder().email(email).username("diffuser").issuedAtDate(iat).expirationDate(exp).build();
    User user = User.builder().id(1L).email(email).username(username).password(encPass).build();
    // When
    Boolean isValid = underTest.isTokenValid(user, token);

    // Then
    assertThat(isValid).isFalse();
  }
}