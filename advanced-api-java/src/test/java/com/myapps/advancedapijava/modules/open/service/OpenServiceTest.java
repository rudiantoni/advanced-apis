package com.myapps.advancedapijava.modules.open.service;

import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenServiceTest {
  @Mock
  private UserRepository repository;
  private OpenService underTest;
  @Test
  @DisplayName("Given an user login info, when it makes login, it should login.")
  void login() {
    // Given
    User userA = User.builder().id(1L).email("user.test@email.com").username("user.test").password("user").build();
    /*
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findByUsernameIgnoreCase(String username);
     */
  }

  @Test
  void createUser() {
  }




}