package com.myapps.bavariamunich.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
            List<String> securityPublicRoutes = Objects.requireNonNull(
                    objectMapper.readValue(env.getRequiredProperty("app.security.public-routes"), new TypeReference<List<String>>() {
                    }),
                    "Unable to parse app.security.public-routes"
            );
            AppProperties.setSecurityPublicRoutes(securityPublicRoutes);
        } catch (Exception e) {
            logger.error("Unable to initialize properties.", e);
            throw new IllegalStateException("Unable to initialize properties.", e);
        }
    }

}
