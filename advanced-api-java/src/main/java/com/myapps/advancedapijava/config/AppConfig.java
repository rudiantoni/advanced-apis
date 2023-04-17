package com.myapps.advancedapijava.config;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class AppConfig {
  private final Environment env;
  private final UserService userService;
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
    AppProperties.appOpenServerUrl = env.getRequiredProperty("app.openapi.server-url");
    AppProperties.appUrlPort = env.getRequiredProperty("server.port");
    AppProperties.appContextPath = env.getRequiredProperty("server.servlet.context-path");
  }

  public void initializeTestData() {
    try {
      User userA = User.builder().email("john.travolta@user.com").username("john").password("john123").build();
      User userB = User.builder().email("will.smith@user.com").username("will").password("will123").build();
      User userC = User.builder().email("Jim Carrey").username("jim").password("jim123").build();
      User userD = User.builder().email("Arnold Schwarzenegger").username("arnold").password("arnold123").build();

      userService.saveUser(userA);
      userService.saveUser(userB);
      userService.saveUser(userC);
      userService.saveUser(userD);
    } catch (Exception e) {
      logger.error("Error when generating starting users: %s.".formatted(e.getMessage()));
      e.printStackTrace();
    }

  }

}
