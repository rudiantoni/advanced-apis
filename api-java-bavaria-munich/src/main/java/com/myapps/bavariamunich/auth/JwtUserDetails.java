package com.myapps.bavariamunich.auth;

public class JwtUserDetails {
    private final String username;
    private final Long userId;
    private final String email;

    public JwtUserDetails(String username, Long userId, String email) {
        this.username = username;
        this.userId = userId;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
