package com.myapps.advancedapijava.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
  UNHANDLED_EXCEPTION("ERR0", "Unhandled exception", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_NOT_FOUND_BY_ID("ERR1", "Product with id not found", HttpStatus.NOT_FOUND),
  PRODUCT_REQUIRED_NAME("ERR2", "Product name is required", HttpStatus.BAD_REQUEST),
  PRODUCT_REQUIRED_DESCRIPTION("ERR3", "Product description is required", HttpStatus.BAD_REQUEST),
  USER_EXISTS_EMAIL("ERR4", "User with email already exists", HttpStatus.BAD_REQUEST),
  USER_EXISTS_USERNAME("ERR5", "User with username already exists", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_EMAIL("ERR6", "User email is required", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_USERNAME("ERR7", "User username is required", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_PASSWORD("ERR8", "User password is required", HttpStatus.BAD_REQUEST),
  LOGIN_REQUIRED_EMAIL_OR_USERNAME("ERR9", "Login email or username is required", HttpStatus.BAD_REQUEST),
  LOGIN_REQUIRED_PASSWORD("ERR10", "Login password is required", HttpStatus.BAD_REQUEST),
  LOGIN_INVALID_LOGIN_OR_PASSWORD("ERR11", "Invalid login or password", HttpStatus.BAD_REQUEST),
  EMAIL_FORMAT_INVALID("ERR12", "Email format is invalid", HttpStatus.BAD_REQUEST),

;
  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
