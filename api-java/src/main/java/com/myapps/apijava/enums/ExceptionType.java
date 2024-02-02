package com.myapps.apijava.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {
  UNHANDLED_EXCEPTION(0, HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled exception."),
  INVALID_EMAIL_OR_PASSWORD(1, HttpStatus.UNAUTHORIZED, "Invalid email or password.");
  private final Integer errorCode;
  private final HttpStatus httpStatus;
  private final String message;
}
