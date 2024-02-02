package com.myapps.apijava.controller;

import com.myapps.apijava.auth.JwtService;
import com.myapps.apijava.dto.LoginReqDto;
import com.myapps.apijava.dto.LoginRespDto;
import com.myapps.apijava.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final JwtService jwtService;

  @PostMapping("/login")
  public ResponseEntity<LoginRespDto> login(
    @RequestBody LoginReqDto loginReqDto
  ) {
    // Token já consegue ser gerado, encodado e decodado OK
    // Token ja consegue ser autenticado no filtro OK
    // TODO: continuar aqui
    // Falta:
    // Validar dados antes de gerar o token (senha, email, etc) pesquisa pelo usuário
    // Adicionar mais dados ao token: permissões nos claims?
    // Atribuir os roles e authorities ao usuário quando receber o token no filtro
    // Manter o token autocontido
    // Testar o acesso ao Token nos endpoints
    // Manipulação de erro no securityconfig
    // Por último, preparar endpoint de criação de user (email único)
    User user = User.builder()
      .id(1L)
      .email("email@test.com")
      .username("Testing User")
      .build();
    String jwtToken = jwtService.createToken(user);

    System.out.println("AUTH LOGIN: You logged in with a new token %s.".formatted(jwtToken));
    return new ResponseEntity<>(null, HttpStatus.OK);
  }
}
