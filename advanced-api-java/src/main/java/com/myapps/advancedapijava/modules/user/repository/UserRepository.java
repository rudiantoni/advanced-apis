package com.myapps.advancedapijava.modules.user.repository;

import com.myapps.advancedapijava.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByEmailIgnoreCase(String email);
  Boolean existsByUsernameIgnoreCase(String email);
  Optional<User> findByEmailIgnoreCase(String email);
  Optional<User> findByUsernameIgnoreCase(String username);

}
