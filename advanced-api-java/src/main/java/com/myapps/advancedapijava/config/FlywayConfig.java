package com.myapps.advancedapijava.config;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.util.Util;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Profile("test-flyway")
public class FlywayConfig {
  private Environment env;

  public FlywayConfig(Environment env) {
    this.env = env;
  }

  Logger logger = Util.getLogger(this.getClass());

  /**
   * Para uso caso não estiver sendo usado o Spring Data JPA
   */
  @PostConstruct
  public void flywayMigration() {
    // Uso das variáveis padrão do Spring Data JPA
    String url = env.getProperty("spring.datasource.url");
    String user = env.getProperty("spring.datasource.username");
    String pass = env.getProperty("spring.datasource.password");
    // Uso das variáveis do Spring + Flyway
//    String url = env.getRequiredProperty("spring.flyway.url");
//    String user = env.getRequiredProperty("spring.flyway.user");
//    String pass = env.getRequiredProperty("spring.flyway.password");
    Boolean enabled = env.getProperty("spring.flyway.enabled", Boolean.class, true);
    Boolean outOfOrder = env.getProperty("spring.flyway.out-of-order", Boolean.class, true);
    String schemas = env.getProperty("spring.flyway.schemas", "public");

    if (enabled) {
      logger.info("Flyway is running in manual mode.");
      Flyway flyway = Flyway.configure()
        .dataSource(url, user, pass)
        .outOfOrder(outOfOrder)
        .schemas(schemas)
        .load();
      flyway.migrate();
    }

  }
}
