package com.myapps.advancedapijava.config;

import java.util.List;

public class AppProperties {
  private AppProperties() {
  }

  public static String openServerUrl;
  public static String urlPort;
  public static String contextPath;
  public static String securitySecretKey;
  public static Integer securityTokenExpirationHours;
  public static String securityTokenPrefix;
  public static List<String> nonSecuredUrlList;
}
