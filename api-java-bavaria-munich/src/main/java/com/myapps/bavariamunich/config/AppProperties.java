package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.auth.PublicRouteDefinition;

import java.util.List;

public class AppProperties {

    private AppProperties() {
    }

    private static String securityJwtSecret;
    private static Long securityJwtExpirationMs;
    private static List<PublicRouteDefinition> securityPublicRoutes;
    private static String securitySuperUserEmail;
    private static String securitySuperUserPassword;
    private static Long securitySuperUserId;
    private static String securitySuperUserUsername;

    public static String getSecurityJwtSecret() {
        return securityJwtSecret;
    }

    public static void setSecurityJwtSecret(String value) {
        if (securityJwtSecret == null) {
            securityJwtSecret = value;
        }
    }

    public static Long getSecurityJwtExpirationMs() {
        return securityJwtExpirationMs;
    }

    public static void setSecurityJwtExpirationMs(Long value) {
        if (securityJwtExpirationMs == null) {
            securityJwtExpirationMs = value;
        }
    }

    public static List<PublicRouteDefinition> getSecurityPublicRoutes() {
        return securityPublicRoutes;
    }

    public static void setSecurityPublicRoutes(List<PublicRouteDefinition> value) {
        if (securityPublicRoutes == null) {
            securityPublicRoutes = value;
        }
    }

    public static String getSecuritySuperUserEmail() {
        return securitySuperUserEmail;
    }

    public static void setSecuritySuperUserEmail(String value) {
        if (securitySuperUserEmail == null) {
            securitySuperUserEmail = value;
        }
    }

    public static String getSecuritySuperUserPassword() {
        return securitySuperUserPassword;
    }

    public static void setSecuritySuperUserPassword(String value) {
        if (securitySuperUserPassword == null) {
            securitySuperUserPassword = value;
        }
    }

    public static Long getSecuritySuperUserId() {
        return securitySuperUserId;
    }

    public static void setSecuritySuperUserId(Long value) {
        if (securitySuperUserId == null) {
            securitySuperUserId = value;
        }
    }

    public static String getSecuritySuperUserUsername() {
        return securitySuperUserUsername;
    }

    public static void setSecuritySuperUserUsername(String value) {
        if (securitySuperUserUsername == null) {
            securitySuperUserUsername = value;
        }
    }

}
