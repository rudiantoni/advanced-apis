package com.myapps.apijava.exception;

import com.myapps.apijava.enums.ExceptionType;
import lombok.Getter;

@Getter
public class HandledException extends Exception {
  private final ExceptionType exceptionType;
  private final String customMessage;

  public HandledException(ExceptionType exceptionType) {
    super(exceptionType.getMessage());
    this.exceptionType = exceptionType;
    customMessage = exceptionType.getMessage();
  }

  public HandledException(ExceptionType exceptionType, String customMessage) {
    super(customMessage);
    this.exceptionType = exceptionType;
    this.customMessage = customMessage;
  }
}
