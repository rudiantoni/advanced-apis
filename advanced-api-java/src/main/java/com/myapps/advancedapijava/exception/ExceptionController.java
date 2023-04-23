package com.myapps.advancedapijava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionHandler(Exception e) {
    System.out.println("Chamado exception handler.");
    System.out.println(e.getMessage());
    System.out.println("Resposta do manipulador de exceções: %s".formatted(e.getMessage()));

    e.printStackTrace();

    return new ResponseEntity<>("Resposta do manipulador de exceções: %s".formatted(e.getMessage()), HttpStatus.NOT_FOUND);
  }

}
