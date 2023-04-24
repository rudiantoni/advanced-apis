package com.myapps.advancedapijava.exception;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.enums.ExceptionType;
import com.myapps.advancedapijava.util.Util;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.Optional;

@ControllerAdvice
public class ExceptionController {
  Logger logger = Util.getLogger(this.getClass());

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HandledExceptionDto> exceptionHandler(Exception e) {

    /**
     * Ao dar throw em uma HandledException ou qualquer uma de suas classes que se extendam,
     * é possível informar uma mensagem e/ou enum válido.
     *
     * Caso contrário ele assumirá os valores padrão.
     *
     */

    ExceptionType defaultError = ExceptionType.UNHANDLED_EXCEPTION;
    String defaultMessage = Optional.ofNullable(e.getMessage()).orElse("No error message available");

    MutablePair<HandledExceptionDto, HttpStatus> handledResponseData = new MutablePair<>();

    if (e instanceof HandledException) {
      HandledException ex = (HandledException) e;
      HandledExceptionDto exceptionDto = HandledExceptionDto.builder()
        .timestamp(new Date())
        .error(ex.getError().getCode())
        .message(ex.getErrorMsg())
        .status(ex.getError().getHttpStatus().value())
        .build();
      handledResponseData.setLeft(exceptionDto);
      handledResponseData.setRight(ex.getError().getHttpStatus());
    } else {
      HandledExceptionDto exceptionDto = HandledExceptionDto.builder()
        .timestamp(new Date())
        .error(defaultError.getCode())
        .message(defaultMessage)
        .status(defaultError.getHttpStatus().value())
        .build();
      handledResponseData.setLeft(exceptionDto);
      handledResponseData.setRight(defaultError.getHttpStatus());
    }

    HandledExceptionDto errorData = handledResponseData.getLeft();
    HttpStatus errorStatus = handledResponseData.getRight();

    logger.error(errorData.getMessage());

    return new ResponseEntity<>(errorData, errorStatus);
  }

}
