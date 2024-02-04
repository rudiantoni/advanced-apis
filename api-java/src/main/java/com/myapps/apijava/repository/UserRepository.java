package com.myapps.apijava.repository;

import com.myapps.apijava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE UPPER(u.email) = UPPER(:email)")
  User findActiveByEmail(@Param("email") String email);
  
  @Query("SELECT (COUNT(u) > 0) FROM User u WHERE UPPER(u.email) = UPPER(:email)")
  Boolean existsByEmail(@Param("email") String email);

}
