package com.myapps.apijava.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
  private String username;
  private String email;
  private OffsetDateTime issuedAt;
  private OffsetDateTime expiration;
}
