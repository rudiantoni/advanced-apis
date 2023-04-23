package com.myapps.advancedapijava.modules.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
  private String username;
  private String email;
  private Date issuedAtDate;
  private Date expirationDate;
}
