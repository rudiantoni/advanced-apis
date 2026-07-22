package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.component.AuthDetailsComponent;
import com.myapps.bavariamunich.dto.UserMeResponseDto;
import com.myapps.bavariamunich.dto.UserFullRequestDto;
import com.myapps.bavariamunich.dto.UserSecureResponseDto;
import com.myapps.bavariamunich.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthDetailsComponent authDetailsComponent;
    private final UserService userService;

    public UserController(AuthDetailsComponent authDetailsComponent, UserService userService) {
        this.authDetailsComponent = authDetailsComponent;
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserMeResponseDto> me() {
        JwtUserDetails userDetails = authDetailsComponent.getCurrentUser();
        UserMeResponseDto result = new UserMeResponseDto(
                userDetails.getUserId(),
                userDetails.getEmail(),
                userDetails.getUsername()
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserSecureResponseDto> create(@RequestBody UserFullRequestDto userFullRequestDto) {
        UserSecureResponseDto result = userService.create(userFullRequestDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
