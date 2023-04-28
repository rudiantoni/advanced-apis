package com.myapps.advancedapijava.modules.user.repository;

import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.modules.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {
  @Autowired
  private UserRepository underTest;

  @AfterEach
  void tearDown() {
    underTest.deleteAll();
  }

  @Test
  @DisplayName("Given email and a user, when it checks for users exists by email, is should exists.")
  void itShouldCheckWhenUserEmailExists() {
    // Given
    String email = "test@example.com";
    User user = User.builder().email(email).username("testuser").password("password").build();
    underTest.save(user);
    // When
    Boolean exists = underTest.existsByEmailIgnoreCase(email);
    // Then
    assertThat(exists).isTrue();
  }

  @Test
  @DisplayName("Given a email, when it checks for users exists by email, is should not exists.")
  void itShouldCheckWhenUserEmailDoesNotExists() {
    // Given
    String email = "test@example.com";
    // When
    Boolean exists = underTest.existsByEmailIgnoreCase(email);
    // Then
    assertThat(exists).isFalse();
  }

  @Test
  @DisplayName("Given username and a user, when it checks for users exists by username, is should exists.")
  void itShouldCheckWhenUserUsernameExists() {
    // Given
    String username = "testuser";
    User user = User.builder().email("test@example.com").username(username).password("password").build();
    underTest.save(user);
    // When
    Boolean exists = underTest.existsByUsernameIgnoreCase(username);
    // Then
    assertThat(exists).isTrue();
  }

  @Test
  @DisplayName("Given a username, when it checks for users exists by username, is should not exists.")
  void itShouldCheckWhenUserUsernameDoesNotExists() {
    // Given
    String username = "testuser";
    // When
    Boolean expected = underTest.existsByUsernameIgnoreCase(username);
    // Then
    assertThat(expected).isFalse();
  }

  @Test
  @DisplayName("Given email and a user, when it searches for a user by email, it should find it and it should be equals to the user.")
  void itShouldFindAndBeEqualsUserByEmail() {
    // Given
    String email = "test@example.com";
    User user = User.builder().email(email).username("testuser").password("password").build();
    underTest.save(user);
    // When
    Optional<User> foundUser = underTest.findByEmailIgnoreCase(email);
    // Then
    assertThat(foundUser).isPresent();
    assertThat(foundUser.get())
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);

  }

  @Test
  @DisplayName("Given a email, when it searches for a user by email, it should not find it.")
  void itShouldNotFindUserByEmail() {
    // Given
    String email = "test@example.com";
    // When
    Optional<User> foundUser = underTest.findByEmailIgnoreCase(email);
    // Then
    assertThat(foundUser).isEmpty();

  }

  @Test
  @DisplayName("Given username and a user, when it searches for a user by username, it should find it and it should be equals to the user.")
  void itShouldFindAndBeEqualsUserByUsername() {
    // Given
    String username = "testuser";
    User user = User.builder().email("test@example.com").username(username).password("password").build();
    underTest.save(user);
    // When
    Optional<User> foundUser = underTest.findByUsernameIgnoreCase(username);
    // Then
    assertThat(foundUser).isPresent();
    assertThat(foundUser.get())
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);

  }

  @Test
  @DisplayName("Given a username, when it searches for a user by username, it should not find it.")
  void itShouldNotFindUserByUsername() {
    // Given
    String username = "testuser";
    // When
    Optional<User> foundUser = underTest.findByUsernameIgnoreCase(username);
    // Then
    assertThat(foundUser).isEmpty();

  }

}