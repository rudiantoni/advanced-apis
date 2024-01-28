package com.myapps.apijava.controller;

import com.myapps.apijava.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open")
public class OpenController {

  @GetMapping("/check")
  public ResponseEntity<?> getAll () {
    try {
      System.out.println("Vou tentar fazer algo");
      return new ResponseEntity(new ErrorDto("Não deu erro"), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity(new ErrorDto("Erro interno do servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
