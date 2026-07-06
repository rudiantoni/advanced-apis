package com.myapps.bavariamunich.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapps.bavariamunich.definition.PublicRouteDefinition;
import com.myapps.bavariamunich.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Objects;

@Configuration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final Environment env;
    private final ObjectMapper objectMapper;

    public AppConfig(Environment env, ObjectMapper objectMapper) {
        this.env = env;
        this.objectMapper = objectMapper;
        init();
    }

    private void init() {
        logger.info("AppConfig initialization started");
        initializeJsonUtil();
        initializeProperties();
        logger.info("AppConfig initialization finished");
    }

    private void initializeJsonUtil() {
        JsonUtil.setObjectMapper(objectMapper);
    }

    private void initializeProperties() {
        try {
            AppProperties.setSecurityJwtSecret(env.getRequiredProperty("app.security.jwt-secret"));
            AppProperties.setSecurityJwtExpirationMs(env.getRequiredProperty("app.security.jwt-expiration-ms", Long.class));
            AppProperties.setSecurityPublicRoutes(getSecurityPublicRoutes());
        } catch (Exception e) {
            logger.error("Unable to initialize properties.", e);
            throw new IllegalStateException("Unable to initialize properties.", e);
        }
    }

    private List<PublicRouteDefinition> getSecurityPublicRoutes() throws JsonProcessingException {
        String property = "app.security.public-routes";
        String propertyValue = env.getRequiredProperty(property);

        List<PublicRouteDefinition> securityPublicRoutes = Objects.requireNonNull(
                objectMapper.readValue(propertyValue, new TypeReference<List<PublicRouteDefinition>>() {
                }),
                "Unable to parse " + property
        );

        securityPublicRoutes.stream()
                .filter(it -> it.getRoute() == null || it.getRoute().trim().isEmpty())
                .findFirst()
                .ifPresent(ignored -> {
                    throw new IllegalStateException(String.format("Each public route must have a non-empty route." +
                            "\nProperty %s raw value: %s" +
                            "\nParsed routes=%s", property, propertyValue, securityPublicRoutes));
                });

        return securityPublicRoutes;
    }

}
