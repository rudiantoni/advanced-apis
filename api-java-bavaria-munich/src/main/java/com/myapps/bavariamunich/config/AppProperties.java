package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.definition.PublicRouteDefinition;

import java.util.List;

public class AppProperties {

    private AppProperties() {
    }

    private static String securityJwtSecret;
    private static Long securityJwtExpirationMs;
    private static List<PublicRouteDefinition> securityPublicRoutes;

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

}
