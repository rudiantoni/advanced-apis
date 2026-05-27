package com.myapps.bavariamunich.config;

import java.util.List;

public class AppProperties {
    private AppProperties() {
    }

    private static String securityApiKey;
    private static List<String> securityPublicRoutes;

    public static void setSecurityApiKey(String givenSecurityApiKey) {
        if (securityApiKey == null) {
            securityApiKey = givenSecurityApiKey;
        }
    }

    public static String getSecurityApiKey() {
        return securityApiKey;
    }

    public static void setSecurityPublicRoutes(List<String> givenSecurityPublicRoutes) {
        if (securityPublicRoutes == null) {
            securityPublicRoutes = givenSecurityPublicRoutes;
        }
    }

    public static List<String> getSecurityPublicRoutes() {
        return securityPublicRoutes;
    }

}
