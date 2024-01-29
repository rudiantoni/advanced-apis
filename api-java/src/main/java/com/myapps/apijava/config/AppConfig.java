package com.myapps.apijava.config;

import ch.qos.logback.classic.Logger;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AppConfig {
  private final Environment env;

  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @Bean
  public List<String> openUrls() {
    return List.of(
      "/open/**",
      "/auth/login"
    );
  }

  @PostConstruct
  public void init() {
    initializeLocalization();
    initializeProperties();
  }

  public void initializeLocalization() {
    Locale.setDefault(new Locale("pt", "BR"));
    TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
  }

  public void initializeProperties() {
    try {
      AppProperties.securitySecretKey = env.getRequiredProperty("app.security.secret-key", String.class);
      AppProperties.securityTokenExpirationHours = env.getRequiredProperty("app.security.token.expiration-hours", Integer.class);
      AppProperties.securityTokenPrefix = env.getRequiredProperty("app.security.token.prefix", String.class);
    } catch (IllegalStateException e) {
      logger.error("Unable to initialize property from environment variables. Check your environment and/or profiles definition.");
      logger.error(e.getMessage());
    } catch (ConversionFailedException e) {
      logger.error("Unable to convert property to target type from environment variables. Check your environment and/or profiles definition.");
      logger.error(e.getMessage());
    }
  }
}
