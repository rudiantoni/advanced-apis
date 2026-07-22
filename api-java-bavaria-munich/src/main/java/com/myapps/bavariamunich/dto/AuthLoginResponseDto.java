package com.myapps.bavariamunich.dto;

public class AuthLoginResponseDto {
    private String token;

    public AuthLoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
