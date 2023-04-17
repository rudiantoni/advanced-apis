package com.myapps.advancedapijava.modules.user.repository;

import com.myapps.advancedapijava.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByEmail(String email);
  User findByEmail(String email);

  Optional<User> findByUsername(String username);
}
