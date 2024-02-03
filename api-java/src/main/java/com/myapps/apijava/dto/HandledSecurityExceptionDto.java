package com.myapps.apijava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandledSecurityExceptionDto {
  private String timestamp;
  private Integer httpStatus;
  private Integer errorCode;
  private String message;
}
