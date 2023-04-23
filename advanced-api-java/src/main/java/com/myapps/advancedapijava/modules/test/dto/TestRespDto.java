package com.myapps.advancedapijava.modules.test.dto;

import com.myapps.advancedapijava.modules.auth.dto.Token;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestRespDto {
  private String status;
  private String tokenStr;
  private Token token;
}
