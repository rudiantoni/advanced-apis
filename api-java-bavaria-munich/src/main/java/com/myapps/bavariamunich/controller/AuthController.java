package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.AuthLoginRequestDto;
import com.myapps.bavariamunich.dto.AuthLoginResponseDto;
import com.myapps.bavariamunich.exception.ErrorCode;
import com.myapps.bavariamunich.exception.MultiErrorException;
import com.myapps.bavariamunich.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDto> login(@RequestBody AuthLoginRequestDto body) {
        String token = authService.login(body);
        if (token == null) {
            throw new MultiErrorException(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED);
        }
        AuthLoginResponseDto result = new AuthLoginResponseDto(token);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
