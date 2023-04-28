package com.myapps.advancedapijava.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {
  UNHANDLED_EXCEPTION("ERR0", "Unhandled exception.", HttpStatus.INTERNAL_SERVER_ERROR),
  PRODUCT_NOT_FOUND_WITH_ID("ERR1", "Product with id not found.", HttpStatus.NOT_FOUND),
  PRODUCT_NOT_FOUND_BY_ID("ERR2", "Product with id %s not found.", HttpStatus.NOT_FOUND),
  PRODUCT_REQUIRED_NAME("ERR3", "Product name is required.", HttpStatus.BAD_REQUEST),
  PRODUCT_REQUIRED_DESCRIPTION("ERR4", "Product description is required.", HttpStatus.BAD_REQUEST),
  USER_EXISTS_WITH_EMAIL("ERR5", "User with email already exists.", HttpStatus.BAD_REQUEST),
  USER_EXISTS_BY_EMAIL("ERR6", "User with email %s already exists.", HttpStatus.BAD_REQUEST),
  USER_EXISTS_WITH_USERNAME("ERR7", "User with username already exists.", HttpStatus.BAD_REQUEST),
  USER_EXISTS_BY_USERNAME("ERR8", "User with username %s already exists.", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_EMAIL("ERR9", "User email is required.", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_USERNAME("ERR10", "User username is required.", HttpStatus.BAD_REQUEST),
  USER_REQUIRED_PASSWORD("ERR11", "User password is required.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND_WITH_EMAIL("ERR12", "User with email not found.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND_BY_EMAIL("ERR13", "User with email %s not found.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND_WITH_USERNAME("ERR14", "User with username not found.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND_BY_USERNAME("ERR15", "User with username %s not found.", HttpStatus.BAD_REQUEST),
  LOGIN_REQUIRED_EMAIL_OR_USERNAME("ERR16", "Login email or username is required.", HttpStatus.BAD_REQUEST),
  LOGIN_REQUIRED_PASSWORD("ERR17", "Login password is required.", HttpStatus.BAD_REQUEST),
  LOGIN_INVALID_LOGIN_OR_PASSWORD("ERR18", "Invalid login or password.", HttpStatus.BAD_REQUEST),
  EMAIL_FORMAT_INVALID("ERR19", "Email format is invalid.", HttpStatus.BAD_REQUEST),

;
  private final String code;
  private final String message;
  private final HttpStatus httpStatus;
}
