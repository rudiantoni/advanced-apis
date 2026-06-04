package com.myapps.bavariamunich.config;

public class AppProperties {
    private AppProperties() {
    }

    private static String myProperty;

    public static void setMyProperty(String givenMyProperty) {
        if (myProperty == null) {
            myProperty = givenMyProperty;
        }
    }

    public static String getMyProperty() {
        return myProperty;
    }
}
