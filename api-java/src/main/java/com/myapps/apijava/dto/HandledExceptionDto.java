package com.myapps.apijava.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandledExceptionDto {
  private OffsetDateTime timestamp;
  private Integer httpStatus;
  private Integer errorCode;
  private String message;
}
