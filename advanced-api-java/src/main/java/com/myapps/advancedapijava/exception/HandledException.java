package com.myapps.advancedapijava.exception;

import com.myapps.advancedapijava.enums.ExceptionType;
import lombok.Getter;

@Getter
public class HandledException extends Exception {
  public HandledException(String errorMsg) {
    super(errorMsg);
  }

  public HandledException(ExceptionType error) {
    super(error.getMessage());
    this.error = error;
    this.errorMsg = error.getMessage();
  }

  public HandledException(String errorMsg, ExceptionType error) {
    super(errorMsg);
    this.error = error;
    this.errorMsg = errorMsg;
  }

  private ExceptionType error = ExceptionType.UNHANDLED_EXCEPTION;
  private String errorMsg = error.getMessage();
}
