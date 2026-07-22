package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.config.AppConsts;
import com.myapps.bavariamunich.controller.base.RequestBodyController;
import com.myapps.bavariamunich.dto.AuthLoginRequestDto;
import com.myapps.bavariamunich.dto.AuthLoginResponseDto;
import com.myapps.bavariamunich.dto.ErrorResponseDto;
import com.myapps.bavariamunich.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController extends RequestBodyController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginRequestDto body) {
        String token = authService.login(body);
        if (token == null) {
            ErrorResponseDto result = ErrorResponseDto.of(AppConsts.UNAUTHORIZED);
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
        AuthLoginResponseDto result = new AuthLoginResponseDto(token);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
}
