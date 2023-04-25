package com.myapps.advancedapijava.modules.user.repository;

import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.util.CryptUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
  void itShouldCheckWhenUserEmailExists() {
    // given
    String email = "user.test@email.com";
    User user = new User();
    user.setEmail(email);
    user.setUsername("user.test");
    user.setPassword("user");
    user.getEmail();
    user.getUsername();
    user.getPassword();
    underTest.save(user);
    User.builder().build();
    // when
    Boolean expected = underTest.existsByEmailIgnoreCase(email);
    // then
    assertThat(expected).isTrue();

  }

  @Test
  void itShouldCheckWhenUserEmailDoesNotExists() {
    // given
    String email = "user.testNoExists@email.com";
    // when
    Boolean expected = underTest.existsByEmailIgnoreCase(email);
    // then
    assertThat(expected).isFalse();
  }

  @Test
  void existsByUsernameIgnoreCase() {
  }

  @Test
  void findByEmailIgnoreCase() {
  }

  @Test
  void findByUsernameIgnoreCase() {
  }

  @Test
  void findByEmail() {
  }

  @Test
  void findByUsername() {
  }
}