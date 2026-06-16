package com.myapps.bavariamunich.config;

public class AppConsts {

    private AppConsts() {
    }

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String DEFAULT_UNAUTHORIZED_RESPONSE_JSON_STR = "{\"message\":\"Unauthorized\"}";

    // Credenciais fixas (temporário)
    public static final String AUTH_EMAIL = "admin@mail.com";
    public static final String AUTH_PASSWORD = "adminpass";
    public static final Long EXAMPLE_USER_ID = 1L;
    public static final String EXAMPLE_USERNAME = "admin";
}
