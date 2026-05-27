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
        initializeProperties();
    }

    public void initializeProperties() {
        try {
            AppProperties.setSecurityApiKey(env.getRequiredProperty("app.security.api-key", String.class));
            AppProperties.setSecurityPublicRoutes(ListUtil.splitToList(
                    env.getRequiredProperty("app.security.public-routes", String.class), ";"
            ));
        } catch (IllegalStateException e) {
            logger.error("Unable to initialize property from environment variables. Check your environment and/or profiles definition.");
            logger.error(e.getMessage());
        } catch (ConversionFailedException e) {
            logger.error("Unable to convert property to target type from environment variables. Check your environment and/or profiles definition.");
            logger.error(e.getMessage());
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
