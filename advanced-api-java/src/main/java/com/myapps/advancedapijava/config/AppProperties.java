package com.myapps.advancedapijava.config;

public class AppProperties {
  private AppProperties() {
  }

  public static String appOpenServerUrl;
  public static String appUrlPort;
  public static String appContextPath;
  public static String secretKey;
  public static Integer tokenExpirationHours;

}
