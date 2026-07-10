package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.auth.PublicRouteRule;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String BEARER_AUTH = "bearerAuth";

    @Bean
    public OpenAPI openApi() {
        Info info = new Info()
                .title("api-java-bavaria-munich")
                .version("v1");

        Components components = new Components()
                .addSecuritySchemes(BEARER_AUTH, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                );

        return new OpenAPI()
                .info(info)
                .components(components);
    }

    @Bean
    public OpenApiCustomiser publicRoutesOpenApiCustomiser(
            @Qualifier("publicRouteRules") List<PublicRouteRule> publicRouteRules
    ) {
        return openApi -> {
            if (openApi.getPaths() == null) {
                return;
            }
            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperationsMap().forEach((method, operation) -> {
                    boolean isPublic = publicRouteRules.stream()
                            .anyMatch(rule -> rule.matches(path, method.name()));
                    if (isPublic) {
                        operation.setSecurity(Collections.emptyList());
                    } else {
                        operation.setSecurity(Collections.singletonList(
                                new SecurityRequirement().addList(BEARER_AUTH)));
                    }
                });
            });
        };
    }

}
