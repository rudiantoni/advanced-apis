package com.myapps.advancedapijava.modules.user.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

  private User user;

  @BeforeEach
  public void setUp() {
    user = User.builder()
      .id(1L)
      .email("test@example.com")
      .username("testuser")
      .password("password")
      .build();
  }

  @Test
  @DisplayName("Given a user, when getters are called, then values should be returned.")
  public void itShouldReturnAllUserValues() {
    // Given
    // User
    // When
    Long id = user.getId();
    String email = user.getEmail();
    String username = user.getUsername();
    String password = user.getPassword();
    // Then
    assertThat(id).isEqualTo(1L);
    assertThat(email).isEqualTo("test@example.com");
    assertThat(username).isEqualTo("testuser");
    assertThat(password).isEqualTo("password");
  }

  @Test
  @DisplayName("Given a user, when setters are called, then values should be defined.")
  public void itShouldDefineAllUserValues() {
    // Given
    // User
    // When
    user.setId(2L);
    user.setEmail("newtest@example.com");
    user.setUsername("newtestuser");
    user.setPassword("newpassword");
    // Then
    assertThat(user.getId()).isEqualTo(2L);
    assertThat(user.getEmail()).isEqualTo("newtest@example.com");
    assertThat(user.getUsername()).isEqualTo("newtestuser");
    assertThat(user.getPassword()).isEqualTo("newpassword");
  }

  @Test
  @DisplayName("Given no-args constructor, when it's called, then all values should be null.")
  public void itShouldReturnAllUserValuesAsNull() {
    // Given
    User emptyUser;
    // When
    emptyUser = new User();
    // Then
    assertThat(emptyUser.getId()).isNull();
    assertThat(emptyUser.getEmail()).isNull();
    assertThat(emptyUser.getUsername()).isNull();
    assertThat(emptyUser.getPassword()).isNull();
  }

  @Test
  @DisplayName("Given all-args constructor, when it's called, then all values should be defined.")
  public void itShouldReturnAllUserValuesAsDefined() {
    // Given
    // User
    // When
    User newUser = new User(2L, "newtest@example.com", "newtestuser", "newpassword");
    // Then
    assertThat(newUser.getId()).isEqualTo(2L);
    assertThat(newUser.getEmail()).isEqualTo("newtest@example.com");
    assertThat(newUser.getUsername()).isEqualTo("newtestuser");
    assertThat(newUser.getPassword()).isEqualTo("newpassword");
  }

  @Test
  @DisplayName("Given a user, when builder is called, then all values should be defined.")
  public void itShouldReturnAllUserValuesAsDefinedInBuilder() {
    // Given
    // User
    // When
    User newUser = User.builder()
      .id(2L)
      .email("newtest@example.com")
      .username("newtestuser")
      .password("newpassword")
      .build();
    // Then
    assertThat(newUser.getId()).isEqualTo(2L);
    assertThat(newUser.getEmail()).isEqualTo("newtest@example.com");
    assertThat(newUser.getUsername()).isEqualTo("newtestuser");
    assertThat(newUser.getPassword()).isEqualTo("newpassword");
  }
}