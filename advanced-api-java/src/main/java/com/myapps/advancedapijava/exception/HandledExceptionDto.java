package com.myapps.advancedapijava.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HandledExceptionDto {
  private Date timestamp;
  private String error;
  private String message;
  private Integer status;
}
