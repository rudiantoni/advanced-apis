package com.myapps.advancedapijava.modules.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_seq")
  @SequenceGenerator(name = "users_seq", sequenceName = "public.users_seq", allocationSize = 1)
  @Column(name = "id")
  private Long id;
  @Column(name = "email")
  private String email;
  @Column(name = "username")
  private String username;
  @Column(name = "password")
  private String password;

}
