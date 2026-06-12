package com.myapps.bavariamunich.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
@DependsOn("appConfig")
public class AppBeansConfig {

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
