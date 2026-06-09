package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.dto.LoginRequestDto;
import com.myapps.bavariamunich.dto.LoginResponseDto;
import com.myapps.bavariamunich.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto body) {
        String token = authService.login(body);
        if (token == null) {
            Map<String, String> result = Collections.singletonMap("message", "Unauthorized");
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        LoginResponseDto result = new LoginResponseDto(token);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
