package com.myapps.bavariamunich.service;

import com.myapps.bavariamunich.auth.JwtService;
import com.myapps.bavariamunich.auth.JwtUserDetails;
import com.myapps.bavariamunich.config.AppProperties;
import com.myapps.bavariamunich.dto.LoginRequestDto;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class AuthService {

    private final JwtService jwtService;

    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String login(LoginRequestDto given) {
        if (!isValidCredentials(given.getEmail(), given.getPassword())) {
            return null;
        }
        return jwtService.generateToken(new JwtUserDetails(
                AppProperties.getSecuritySuperUserUsername(),
                AppProperties.getSecuritySuperUserId(),
                given.getEmail()
        ));
    }

    private boolean isValidCredentials(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        return constantTimeEquals(AppProperties.getSecuritySuperUserEmail(), email)
                && constantTimeEquals(AppProperties.getSecuritySuperUserPassword(), password);
    }

    private static boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8)
        );
    }

}
