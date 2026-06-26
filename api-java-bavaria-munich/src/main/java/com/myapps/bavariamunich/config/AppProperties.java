package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.definition.DefaultUserDefinition;
import com.myapps.bavariamunich.definition.PublicRouteDefinition;

import java.util.List;

public class AppProperties {

    private AppProperties() {
    }

    private static String securityJwtSecret;
    private static Long securityJwtExpirationMs;
    private static List<PublicRouteDefinition> securityPublicRoutes;
    private static List<DefaultUserDefinition> securityDefaultUsers;

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

    public static List<DefaultUserDefinition> getSecurityDefaultUsers() {
        return securityDefaultUsers;
    }

    public static void setSecurityDefaultUsers(List<DefaultUserDefinition> value) {
        if (securityDefaultUsers == null) {
            securityDefaultUsers = value;
        }
    }

}
