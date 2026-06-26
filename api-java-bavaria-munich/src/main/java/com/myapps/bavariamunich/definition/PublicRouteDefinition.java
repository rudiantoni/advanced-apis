package com.myapps.bavariamunich.definition;

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

    public List<String> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        return "PublicRouteDefinition{" +
                "route='" + route + '\'' +
                ", methods=" + methods +
                '}';
    }
}