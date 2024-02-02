package com.myapps.apijava.repository;

import com.myapps.apijava.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.active = true")
  User findActiveByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
