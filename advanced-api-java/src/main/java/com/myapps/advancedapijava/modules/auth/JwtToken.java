package com.myapps.advancedapijava.modules.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken {
  private String iss; //issuer
  private String sub; //subject
  private String aud; //audience
  private Long exp; //expiration
  private Long nbf; //not_before
  private Long iat; //issued_at
  private String jti; //jwt id
}
