package com.myapps.advancedapijava.config;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.modules.user.dto.UserDto;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
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
    initializeConstants();
    initializeProperties();
    initializeTestData();
  }

  private void initializeLocalization() {
    Locale.setDefault(new Locale("pt", "BR"));
    TimeZone.setDefault(TimeZone.getTimeZone("GMT-3"));
  }

  private void initializeConstants() {
    List<String> nonSecuredUrlList = Arrays.asList(
      "/open/**",
      "/swagger-ui/**",
      "/v3/api-docs/**"
    );

    AppProperties.nonSecuredUrlList = nonSecuredUrlList;
  }

  private void initializeProperties() {
    try {
      AppProperties.openServerUrl = env.getRequiredProperty("app.openapi.server-url");
      AppProperties.urlPort = env.getRequiredProperty("server.port");
      AppProperties.contextPath = env.getRequiredProperty("server.servlet.context-path");
      AppProperties.securitySecretKey = env.getRequiredProperty("app.security.secret-key");
      AppProperties.securityTokenExpirationHours = env.getRequiredProperty("app.security.token.expiration-hours", Integer.class);
      AppProperties.securityTokenPrefix = env.getRequiredProperty("app.security.token.prefix");

    } catch (IllegalStateException e) {
      logger.error("Unable to initialize property from environment variables. Check your environment and/or profiles definition.");
      e.printStackTrace();
    } catch (ConversionFailedException e) {
      logger.error("Unable to convert property to target type from environment variables. Check your environment and/or profiles definition.");
      e.printStackTrace();
    }
  }

  public void initializeTestData() {

    try {
      UserDto userA = UserDto.builder().email("user@user.com").username("user").password("user").build();
      userService.create(userA);
    } catch (Exception e) {
      logger.error("Error when generating starting users: %s.".formatted(e.getMessage()));
    }

  }

}
