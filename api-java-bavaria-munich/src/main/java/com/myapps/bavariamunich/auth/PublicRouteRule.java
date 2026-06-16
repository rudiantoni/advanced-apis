package com.myapps.bavariamunich.auth;

import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PublicRouteRule {

    private final Pattern pattern;
    private final Set<HttpMethod> methods;

    public PublicRouteRule(String routeRegex, List<String> methodNames) {
        if (routeRegex == null || routeRegex.trim().isEmpty()) {
            throw new IllegalArgumentException("Public route regex must not be blank");
        }
        this.pattern = Pattern.compile(routeRegex);
        this.methods = normalizeMethods(methodNames);
    }

    public boolean matches(String servletPath, String httpMethod) {
        if (servletPath == null || !pattern.matcher(servletPath).matches()) {
            return false;
        }
        if (methods.isEmpty()) {
            return true;
        }
        return methods.contains(HttpMethod.valueOf(httpMethod));
    }

    private static Set<HttpMethod> normalizeMethods(List<String> methodNames) {
        if (methodNames == null || methodNames.isEmpty()) {
            return Collections.emptySet();
        }
        return methodNames.stream()
                .map(String::trim)
                .map(String::toUpperCase)
                .map(HttpMethod::valueOf)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(HttpMethod.class)));
    }

}
