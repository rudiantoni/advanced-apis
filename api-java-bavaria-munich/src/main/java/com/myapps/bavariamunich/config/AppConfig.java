package com.myapps.bavariamunich.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapps.bavariamunich.definition.DefaultUserDefinition;
import com.myapps.bavariamunich.definition.PublicRouteDefinition;
import com.myapps.bavariamunich.util.NormalizeUtil;
import com.myapps.bavariamunich.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
            AppProperties.setSecurityDefaultUsers(getSecurityDefaultUsers());
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

    private List<DefaultUserDefinition> getSecurityDefaultUsers() throws JsonProcessingException {
        String property = "app.security.default-users";
        String propertyValue = env.getRequiredProperty(property);
        List<DefaultUserDefinition> securityDefaultUsersRaw = Objects.requireNonNull(
                objectMapper.readValue(propertyValue, new TypeReference<List<DefaultUserDefinition>>() {
                }),
                "Unable to parse " + property
        );

        securityDefaultUsersRaw.stream()
                .filter(it -> it.getId() == null
                        || it.getEmail() == null || it.getEmail().trim().isEmpty()
                        || it.getUsername() == null || it.getUsername().trim().isEmpty()
                        || it.getPassword() == null || it.getPassword().trim().isEmpty())
                .findFirst()
                .ifPresent(ignored -> {
                    throw new IllegalStateException(String.format(
                            "Each default user must have non-empty id, email, username and password." +
                                    "\nNot logging raw values since sensitive information might be present" +
                                    "\nParsed users=%s", securityDefaultUsersRaw));
                });

        List<DefaultUserDefinition> securityDefaultUsers = securityDefaultUsersRaw.stream()
                .map(it -> new DefaultUserDefinition(
                        it.getId(),
                        NormalizeUtil.normalizeEmail(it.getEmail()),
                        it.getUsername(),
                        it.getPassword()
                ))
                .collect(Collectors.toList());

        long uniqueIdCount = securityDefaultUsers.stream().map(DefaultUserDefinition::getId).distinct().count();
        if (uniqueIdCount != securityDefaultUsers.size()) {
            throw new IllegalStateException(String.format(
                    "Each default user must have a unique id." +
                            "\nNot logging raw values since sensitive information might be present" +
                            "\nParsed users=%s", securityDefaultUsers));
        }

        long uniqueEmailCount = securityDefaultUsers.stream()
                .map(DefaultUserDefinition::getEmail)
                .distinct()
                .count();

        if (uniqueEmailCount != securityDefaultUsers.size()) {
            throw new IllegalStateException(String.format(
                    "Each default user must have a unique email." +
                            "\nNot logging raw values since sensitive information might be present" +
                            "\nParsed users=%s", securityDefaultUsers));
        }

        return securityDefaultUsers;
    }

}
