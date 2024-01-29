package com.myapps.apijava.entity;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
  private Long id;
  private String email;
  private String username;
  private String password;
}
