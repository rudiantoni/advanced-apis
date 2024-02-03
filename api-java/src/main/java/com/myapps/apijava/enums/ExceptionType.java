package com.myapps.apijava.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionType {
  UNHANDLED_EXCEPTION(0, HttpStatus.INTERNAL_SERVER_ERROR, "Unhandled exception."),
  AUTHENTICATION_EXCEPTION(1, HttpStatus.UNAUTHORIZED, "You have no access to this resource."),
  AUTHORIZATION_EXCEPTION(2, HttpStatus.FORBIDDEN, "You are not authorized to access this resource."),
  INVALID_EMAIL_OR_PASSWORD(3, HttpStatus.UNAUTHORIZED, "Invalid email or password.");
  private final Integer errorCode;
  private final HttpStatus httpStatus;
  private final String message;
}
