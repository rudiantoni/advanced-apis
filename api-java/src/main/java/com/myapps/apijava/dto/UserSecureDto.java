package com.myapps.apijava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSecureDto {
  private Long id;
  private String email;
  private String username;
  private List<String> permissions;
}
