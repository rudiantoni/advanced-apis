package com.myapps.bavariamunich.controller;

import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.component.AuthDetailsComponent;
import com.myapps.bavariamunich.dto.MeResponseDto;
import com.myapps.bavariamunich.dto.UserSecureDto;
import com.myapps.bavariamunich.dto.UserWriteDto;
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
    public ResponseEntity<MeResponseDto> me() {
        JwtUserDetails userDetails = authDetailsComponent.getCurrentUser();
        MeResponseDto result = new MeResponseDto(
                userDetails.getUserId(),
                userDetails.getEmail(),
                userDetails.getUsername()
        );
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserSecureDto> create(@RequestBody UserWriteDto userWriteDto) {
        UserSecureDto result = userService.create(userWriteDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
