package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.auth.JwtService;
import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.definition.UserInternalDefinition;
import com.myapps.bavariamunich.dto.LoginRequestDto;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

@Service
public class AuthService {

    private final JwtService jwtService;
    private final UserService userService;

    public AuthService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public String login(LoginRequestDto given) {
        if (given.getEmail() == null || given.getPassword() == null) {
            return null;
        }

        Optional<UserInternalDefinition> foundUser = userService.searchByEmail(given.getEmail());

        if (foundUser.isPresent()) {
            UserInternalDefinition user = foundUser.get();
            if (constantTimeEquals(given.getPassword(), user.getPassword())) {
                return jwtService.generateToken(new JwtUserDetails(
                        user.getUsername(),
                        user.getId(),
                        user.getEmail()
                ));
            }
        }

        return null;
    }

    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return false;
        }
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8)
        );
    }

}
