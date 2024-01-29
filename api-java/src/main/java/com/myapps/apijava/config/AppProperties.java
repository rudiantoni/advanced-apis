package com.myapps.apijava.config;

public class AppProperties {
  private AppProperties() {
  }

  public static String securitySecretKey;
  public static Integer securityTokenExpirationHours;
  public static String securityTokenPrefix;

}
