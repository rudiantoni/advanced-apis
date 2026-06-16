package com.myapps.bavariamunich.auth;

import java.util.List;

public class PublicRouteDefinition {
    private String route;
    private List<String> methods;

    public PublicRouteDefinition() {
    }

    public PublicRouteDefinition(String route, List<String> methods) {
        this.route = route;
        this.methods = methods;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "PublicRouteDefinition{" +
                "route='" + route + '\'' +
                ", methods=" + methods +
                '}';
    }
}