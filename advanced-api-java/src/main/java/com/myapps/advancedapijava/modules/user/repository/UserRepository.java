package com.myapps.advancedapijava.modules.user.repository;

import com.myapps.advancedapijava.modules.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByEmail(String email);
  User findByUsername(String username);
}
