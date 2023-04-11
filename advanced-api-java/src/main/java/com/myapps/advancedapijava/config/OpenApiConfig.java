package com.myapps.advancedapijava.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
  @Bean
  public OpenAPI appOpenApiConfig() {
    String title = "API Advanced Java";
    String description = "Documentação API Advanced Java";
    String version = "1.0.0";
    String serverUrl = AppProperties.appOpenServerUrl;

    return new OpenAPI()
      .addServersItem(
        new Server()
          .url(serverUrl)
      )
      .info(
        new Info()
          .title(title)
          .description(description)
          .version(version)
      );
  }

}
