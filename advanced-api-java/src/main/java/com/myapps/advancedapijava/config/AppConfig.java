package com.myapps.advancedapijava.config;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AppConfig {
  private final Environment env;
  private final PasswordEncoder passwordEncoder;
  Logger logger = Util.getLogger(this.getClass());

  @PostConstruct
  private void init() {
    initializeLocalization();
    initializeProperties();
    initializeTestData();
  }

  private void initializeLocalization() {
    Locale.setDefault(new Locale("pt", "BR"));
    TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
  }

  private void initializeProperties() {
    try {
      AppProperties.appOpenServerUrl = env.getRequiredProperty("app.openapi.server-url");
      AppProperties.appUrlPort = env.getRequiredProperty("server.port");
      AppProperties.appContextPath = env.getRequiredProperty("server.servlet.context-path");
      AppProperties.secretKey = env.getRequiredProperty("app.security.secret-key");
      AppProperties.tokenExpirationHours = env.getRequiredProperty("app.security.token-expiration-hours", Integer.class);
    } catch (IllegalStateException e) {
      logger.error("Unable to initialize property from environment variables. Check your environment and/or profiles definition.");
      e.printStackTrace();
    } catch (ConversionFailedException e) {
      logger.error("Unable to convert property to target type from environment variables. Check your environment and/or profiles definition.");
      e.printStackTrace();
    }
  }

  public void initializeTestData() {
    System.out.println("Password encoder %s = %s".formatted("pass", passwordEncoder.encode("pass")));
    System.out.println("Password encoder %s = %s".formatted("user", passwordEncoder.encode("user")));

    /*
    try {
      User userA = User.builder().email("john.travolta@user.com").username("john").password(passwordEncoder.encode("john123")).build();
      User userB = User.builder().email("will.smith@user.com").username("will").password(passwordEncoder.encode("will123")).build();
      User userC = User.builder().email("Jim Carrey").username("jim").password(passwordEncoder.encode("jim123")).build();
      User userD = User.builder().email("Arnold Schwarzenegger").username("arnold").password(passwordEncoder.encode("arnold123")).build();

      userService.saveUser(userA);
      userService.saveUser(userB);
      userService.saveUser(userC);
      userService.saveUser(userD);
    } catch (Exception e) {
      logger.error("Error when generating starting users: %s.".formatted(e.getMessage()));
      e.printStackTrace();
    }
    */
  }

}
