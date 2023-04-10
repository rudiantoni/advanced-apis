package com.myapps.advancedapijava.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppConfig {
  private Environment env;

  public AppConfig(Environment env) {
    this.env = env;
  }

  @PostConstruct
  private void init() {
    initializeProperties();
  }

  private void initializeProperties() {
    AppProperties.propriedadeTeste = env.getProperty("propriedade.teste");
  }
}
