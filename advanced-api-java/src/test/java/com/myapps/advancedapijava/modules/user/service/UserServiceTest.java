package com.myapps.advancedapijava.modules.user.service;

import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository repository;
  private UserService underTest;

  @BeforeEach
  void setUp() {
    underTest = new UserService(repository);
  }

  @Test
  @DisplayName("Given email and a user, when it searches for a user, it should find it.")
  void itShouldFindUserByEmail() throws HandledException {
    // Given
    String email = "user.test@email.com";
    User user = User.builder().id(1L).email(email).username("user.test").password("user").build();
    Optional<User> optionalUser = Optional.of(user);
    given(repository.findByEmailIgnoreCase(any(String.class))).willReturn(optionalUser);
    // When
    User foundUser = underTest.findByEmailOrException(email);
    // Then
    assertThat(foundUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);
  }

  @Test
  @DisplayName("Given email, when it searches for a user, it will throw an exception.")
  void willThrowWhenNotFoundByEmail() {
    // Given
    String email = "user.test@email.com";
    given(repository.findByEmailIgnoreCase(any(String.class))).willReturn(Optional.empty());
    // When
    // Then
    String message = ExceptionType.USER_NOT_FOUND_BY_EMAIL.getMessage().formatted(email);
    assertThatThrownBy(() -> underTest.findByEmailOrException(email))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(message);
  }

  @Test
  @DisplayName("Given username and a user, when it searches for a user, it should find it.")
  void itShouldFindUserByUsername() throws HandledException {
    // Given
    String username = "user.test";
    User user = User.builder().id(1L).email("user.test@email.com").username(username).password("user").build();
    Optional<User> optionalUser = Optional.of(user);
    given(repository.findByUsernameIgnoreCase(any(String.class))).willReturn(optionalUser);
    // When
    User foundUser = underTest.findByUsernameOrException(username);
    // Then
    assertThat(foundUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);
  }

  @Test
  @DisplayName("Given username, when it searches for a user, it will throw an exception.")
  void willThrowWhenNotFoundByUsername() {
    // Given
    String username = "user.test@email.com";
    given(repository.findByUsernameIgnoreCase(any(String.class))).willReturn(Optional.empty());
    // When
    // Then
    String message = ExceptionType.USER_NOT_FOUND_BY_USERNAME.getMessage().formatted(username);
    assertThatThrownBy(() -> underTest.findByUsernameOrException(username))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(message);
  }

  @Test
  @DisplayName("Given email, when it searches for a user by username or email, it should find it.")
  void itShouldFindUserWhenEmail() {
    String email = "user.test@email.com";
    User user = User.builder().id(1L).email(email).username("user.test").password("user").build();
    Optional<User> optionalUser = Optional.of(user);
    given(repository.findByEmailIgnoreCase(any(String.class))).willReturn(optionalUser);
    // When
    User foundUser = underTest.findByEmailOrUsernameOrNull(email, null);
    // Then
    assertThat(foundUser).isNotNull();
    assertThat(foundUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);
  }

  @Test
  @DisplayName("Given username, when it searches for a user by username or email, it should find it.")
  void itShouldFindUserWhenUsername() {
    String username = "user.test";
    User user = User.builder().id(1L).email("user.test@email.com").username(username).password("user").build();
    Optional<User> optionalUser = Optional.of(user);
    given(repository.findByUsernameIgnoreCase(any(String.class))).willReturn(optionalUser);
    // When
    User foundUser = underTest.findByEmailOrUsernameOrNull(null, username);
    // Then
    assertThat(foundUser).isNotNull();
    assertThat(foundUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(user);
  }

  @Test
  @DisplayName("Given username and email, when it searches for a user by username or email, it should be null.")
  void itShouldNotFindUserWhenEmailOrUsername() {
    // Given
    // When
    when(repository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.empty());
    // Then
    User foundUser = underTest.findByEmailOrUsernameOrNull(null, null);
    assertThat(foundUser).isNull();

    User foundUserA = underTest.findByEmailOrUsernameOrNull("", "");
    assertThat(foundUserA).isNull();

    User foundUserC = underTest.findByEmailOrUsernameOrNull(null, "");
    assertThat(foundUserC).isNull();

    User foundUserD = underTest.findByEmailOrUsernameOrNull("someemail", "");
    assertThat(foundUserD).isNull();

    User foundUserE = underTest.findByEmailOrUsernameOrNull("", "user.test");
    assertThat(foundUserE).isNull();

  }

  @Test
  @DisplayName("Given username, when it searches for all users, it should return them.")
  void canGetAllUsers() {
    // Given
    User userA = User.builder().id(1L).email("user.test@email.com").username("user.test").password("user").build();
    User userB = User.builder().id(2L).email("new.user.test@email.com").username("new.user.test").password("user").build();
    List<User> userList = List.of(userA, userB);
    given(repository.findAll()).willReturn(userList);
    // When
    underTest.findAll();
    // Then
    verify(repository).findAll();
  }

  @Test
  @DisplayName("Given username, when it creates a user, it must exist.")
  void canAddUser() throws HandledException {
    // Given
    String pass = "user"; // default password
    String encPass = CryptUtil.hashSha256(pass); // the crypt pass
    UserDto userDto = UserDto.builder().email("user.test@email.com").username("user.test").password(pass).build(); // the data passed to the function
    User newUser = UserUtil.toEntity(userDto); // the new user from dto
    newUser.setPassword(encPass); // new pass
    // When
    when(repository.save(any())).thenReturn(newUser); // when the .save in repository is called, return this value
    underTest.create(userDto);
    // Then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(repository).save(userArgumentCaptor.capture()); // capture arguments passed to the save repository function
    User capturedUser = userArgumentCaptor.getValue(); // get the entity
    assertThat(capturedUser)
      .usingRecursiveComparison()
      .withStrictTypeChecking()
      .isEqualTo(newUser);

    assertThat(userDto).isNotEqualTo(newUser);
  }

  @Test
  @DisplayName("Given any required field is invalid, it will throw an exception")
  void willThrowWhenRequiredFieldInvalid() {
    // Given
    UserDto userDtoNoEmail = UserDto.builder().email(null).username("user.test").password("user").build();
    UserDto userDtoNoUsername = UserDto.builder().email("user.test@email.com").username(null).password("user").build();
    UserDto userDtoNoPassword = UserDto.builder().email("user.test@email.com").username("user.test").password(null).build();
    // When
    // Then
    assertThatThrownBy(() -> underTest.validateRequiredFields(UserDto.builder().email(null).username("user.test").password("user").build()))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(ExceptionType.USER_REQUIRED_EMAIL.getMessage());

    assertThatThrownBy(() -> underTest.validateRequiredFields(userDtoNoUsername))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(ExceptionType.USER_REQUIRED_USERNAME.getMessage());

    assertThatThrownBy(() -> underTest.validateRequiredFields(userDtoNoPassword))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(ExceptionType.USER_REQUIRED_PASSWORD.getMessage());
  }

  @Test
  @DisplayName("Given any duplicated user by email, it will throw an exception")
  void willThrowWhenEmailDuplicated() {
    // Given
    UserDto userDto = UserDto.builder().email("user.test@email.com").username("user.test").password("user").build();
    // When
    when(repository.existsByEmailIgnoreCase(any(String.class))).thenReturn(true);
    String message = ExceptionType.USER_EXISTS_BY_EMAIL.getMessage().formatted(userDto.getEmail());
    // Then
    assertThatThrownBy(() -> underTest.validateDuplicate(userDto))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(message);
  }

  @Test
  @DisplayName("Given any duplicated user by username, it will throw an exception")
  void willThrowWhenUsernameDuplicated() {
    // Given
    UserDto userDto = UserDto.builder().email("user.test@email.com").username("user.test").password("user").build();
    // When
    when(repository.existsByEmailIgnoreCase(any(String.class))).thenReturn(false);
    when(repository.existsByUsernameIgnoreCase(any(String.class))).thenReturn(true);
    String message = ExceptionType.USER_EXISTS_BY_USERNAME.getMessage().formatted(userDto.getUsername());
    // Then
    assertThatThrownBy(() -> underTest.validateDuplicate(userDto))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining(message);
  }

  @Test
  @DisplayName("Given non duplicated or empty, it will not throw any exception")
  void willNotThrowWhenNotDuplicated() throws HandledException {
    // Given
    UserDto userDto = UserDto.builder().email("").username("").password("user").build();
    // When
    // Then
    underTest.validateDuplicate(userDto);
    verify(repository, never()).existsByEmailIgnoreCase(any(String.class));
    verify(repository, never()).existsByUsernameIgnoreCase(any(String.class));
  }
}