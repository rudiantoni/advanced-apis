package com.myapps.apijava.exception;

import ch.qos.logback.classic.Logger;
import com.myapps.apijava.dto.HandledExceptionDto;
import com.myapps.apijava.dto.HandledSecurityExceptionDto;
import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.OffsetDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HandledExceptionDto> exceptionHandler(Exception e) {
    OffsetDateTime now = OffsetDateTime.now();
    HttpStatus httpStatus = ExceptionType.UNHANDLED_EXCEPTION.getHttpStatus();
    Integer errorCode = ExceptionType.UNHANDLED_EXCEPTION.getErrorCode();
    String message;
    if (e instanceof HandledException) {
      HandledException handledException = (HandledException) e;
      message = handledException.getCustomMessage();
      httpStatus = handledException.getExceptionType().getHttpStatus();
      errorCode = handledException.getExceptionType().getErrorCode();
      logger.info("A handled exception occured: %s".formatted(message));
    } else {
      message = e.getMessage();
      e.printStackTrace();
      logger.error("A unhandled exception occured: %s".formatted(message));
    }

    HandledExceptionDto sendError = HandledExceptionDto.builder()
      .timestamp(now)
      .httpStatus(httpStatus.value())
      .errorCode(errorCode)
      .message(message)
      .build();
    return new ResponseEntity<>(sendError, httpStatus);
  }

  public void handleSecurityException(HttpServletResponse response, ExceptionType exceptionType, Exception e) throws IOException {
    OffsetDateTime now = OffsetDateTime.now();
    HttpStatus httpStatus = exceptionType.getHttpStatus();
    Integer errorCode = exceptionType.getErrorCode();
    String message = exceptionType.getMessage();

    HandledSecurityExceptionDto sendError = HandledSecurityExceptionDto.builder()
      .timestamp(now.toString())
      .httpStatus(httpStatus.value())
      .errorCode(errorCode)
      .message(message)
      .build();

    if (e != null) {
      e.printStackTrace();
    }
    logger.error("A handled security exception occured: %s".formatted(message));

    response.setStatus(httpStatus.value());
    response.setContentType("application/json");
    response.getWriter().write(Util.toJsonStr(sendError));
  }

}
