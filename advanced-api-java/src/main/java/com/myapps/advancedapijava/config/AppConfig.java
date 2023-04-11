package com.myapps.advancedapijava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppConfig {
  private Environment env;

  public AppConfig(Environment env) {
    this.env = env;
  }

  @PostConstruct
  private void init() {
    initializeLocalization();
    initializeProperties();
  }

  private void initializeLocalization() {
    Locale.setDefault(new Locale("pt", "BR"));
    TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
  }

  private void initializeProperties() {
    AppProperties.appOpenServerUrl = env.getRequiredProperty("app.openapi.server-url");
    AppProperties.appUrlPort = env.getRequiredProperty("server.port");
    AppProperties.appContextPath = env.getRequiredProperty("server.servlet.context-path");
  }

}
