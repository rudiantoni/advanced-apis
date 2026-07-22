package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.auth.JwtService;
import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.AuthLoginRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordService passwordService;

    public AuthService(
            JwtService jwtService,
            UserService userService,
            PasswordService passwordService
    ) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordService = passwordService;
    }

    public String login(AuthLoginRequestDto given) {
        if (given.getEmail() == null || given.getPassword() == null) {
            return null;
        }

        Optional<UserInternalDefinition> foundUser = userService.searchByEmail(given.getEmail());

        if (foundUser.isPresent()) {
            UserInternalDefinition user = foundUser.get();
            if (passwordService.matches(given.getPassword(), user.getPassword())) {
                return jwtService.generateToken(new JwtUserDetails(
                        user.getUsername(),
                        user.getId(),
                        user.getEmail()
                ));
            }
        }

        return null;
    }
}
