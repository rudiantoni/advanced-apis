package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.util.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private final Environment env;

    public AppConfig(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void init() {
        logger.info("AppConfig initialization started");
        initializeProperties();
        logger.info("AppConfig initialization finished");
    }

    public void initializeProperties() {
        try {
            AppProperties.setSecurityJwtSecret(env.getRequiredProperty("app.security.jwt-secret", String.class));
            AppProperties.setSecurityJwtExpirationMs(env.getRequiredProperty("app.security.jwt-expiration-ms", Long.class));
            AppProperties.setSecurityPublicRoutes(ListUtil.splitToList(
                    env.getRequiredProperty("app.security.public-routes", String.class), ";"
            ));
        } catch (IllegalStateException e) {
            logger.error("Unable to initialize security properties from environment.", e);
        } catch (ConversionFailedException e) {
            logger.error("Unable to convert security property.", e);
        }
    }

    @Bean
    public List<Pattern> publicUrlPatterns() {
        List<String> routes = AppProperties.getSecurityPublicRoutes();
        if (routes == null) {
            return Collections.emptyList();
        }
        return routes.stream()
                .map(Pattern::compile)
                .collect(Collectors.toList());
    }


}
