package com.myapps.advancedapijava.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRespDto {
  private Long id;
  private String email;
  private String username;
  private String token;
}
