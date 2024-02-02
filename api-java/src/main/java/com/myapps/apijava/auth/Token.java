package com.myapps.apijava.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
  private String email;
  private OffsetDateTime issuedAt;
  private OffsetDateTime expiration;
  private List<String> roles;
  private List<String> authorities;
}
