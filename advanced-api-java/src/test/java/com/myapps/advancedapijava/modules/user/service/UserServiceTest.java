package com.myapps.advancedapijava.modules.user.service;

import com.myapps.advancedapijava.exception.HandledException;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock
  private UserRepository repository;
  private UserService underTest;

  // Substituído pela anotação @ExtendWith
  //private AutoCloseable autoCloseable;
  @BeforeEach
  void setUp() {
    // Substituído pela anotação @ExtendWith
    //autoCloseable = MockitoAnnotations.openMocks(this);
    underTest = new UserService(repository);
  }

  // Substituído pela anotação @ExtendWith
  //@AfterEach void tearDown() throws Exception { autoCloseable.close(); }
  @Test
  @Disabled
  void findByEmailOrException() {
  }

  @Test
  @Disabled
  void findByUsernameOrException() {
  }

  @Test
  @Disabled
  void findByEmailOrUsernameOrNull() {
  }

  @Test
  void canGetAllUsers() {
    // when
    underTest.findAll();
    // then
    verify(repository).findAll();

  }

  @Test
  void canAddUser() throws HandledException {
    // given
    String pass = "user"; // default password
    String encPass = CryptUtil.hashSha256(pass); // the crypt pass
    UserDto userDto = UserDto.builder().email("user.test@email.com").username("user.test").password(pass).build(); // the data passed to the function
    User newUser = UserUtil.toEntity(userDto); // the new user from dto
    newUser.setPassword(encPass); // new pass
    // when
    when(repository.save(any())).thenReturn(newUser); // when the .save in repository is called, return this value
    underTest.create(userDto);
    // then
    ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
    verify(repository).save(userArgumentCaptor.capture()); // capture arguments passed to the save repository function
    User capturedUser = userArgumentCaptor.getValue(); // get the entity
    assertThat(capturedUser).usingRecursiveComparison().withStrictTypeChecking()
      .isEqualTo(newUser);

    assertThat(userDto).isNotEqualTo(newUser);
  }

  @Test
  void willThrowWhenEmailIsTaken(){
    // given
    String pass = "user";
    String encPass = CryptUtil.hashSha256(pass);
    UserDto userDto = UserDto.builder().email("user.test@email.com").username("user.test").password(pass).build();
    given(repository.existsByEmailIgnoreCase(anyString())).willReturn(true);
    // when
    // then
    assertThatThrownBy(() -> underTest.create(userDto))
      .isInstanceOf(HandledException.class)
      .hasMessageContaining("User with email %s already exists.".formatted(userDto.getEmail()));

    verify(repository, never()).save(any()); // This method will not be executed because of the thrown

  }

  @Test
  @Disabled
  void validateRequiredFields() {
  }

  @Test
  @Disabled
  void validateDuplicate() {
  }
}