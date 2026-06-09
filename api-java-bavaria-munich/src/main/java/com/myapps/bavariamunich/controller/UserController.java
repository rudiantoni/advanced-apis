package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.component.AuthDetailsComponent;
import com.myapps.bavariamunich.dto.MeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthDetailsComponent authDetailsComponent;

    public UserController(AuthDetailsComponent authDetailsComponent) {
        this.authDetailsComponent = authDetailsComponent;
    }

    @GetMapping("/me")
    public ResponseEntity<MeResponseDto> me() {
        JwtUserDetails userDetails = authDetailsComponent.getCurrentUser();
        MeResponseDto result = new MeResponseDto(
                userDetails.getUsername(),
                userDetails.getUserId(),
                userDetails.getEmail()
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
