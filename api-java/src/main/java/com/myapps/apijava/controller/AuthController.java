package com.myapps.apijava.controller;

import com.myapps.apijava.auth.JwtService;
import com.myapps.apijava.dto.MsgDto;
import com.myapps.apijava.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<?> login() {
    try {
      // TODO: continuar aqui
      // Token já consegue ser gerado, encodado e decodado, falta poder autenticar no filter, atribuir roles/authorities e pesquisar num repo o usuário
      // Por último, preparar endpoint de criação de user (email único)
      User user = User.builder()
        .id(1L)
        .email("email@test.com")
        .username("Testing User")
        .build();
      String jwtToken = jwtService.createToken(user);

      System.out.println("AUTH LOGIN: You logged in with a new token %s.".formatted(jwtToken));
      return new ResponseEntity<>(new MsgDto("AUTH LOGIN: You logged in with a new token %s".formatted(jwtToken)), HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(new MsgDto("INTERNAL SERVER ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
