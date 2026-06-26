package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.definition.PublicRouteDefinition;
import com.myapps.bavariamunich.auth.PublicRouteRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@DependsOn("appConfig")
public class AppBeansConfig {

    @Bean
    public List<PublicRouteRule> publicRouteRules() {
        List<PublicRouteDefinition> definitions = AppProperties.getSecurityPublicRoutes();
        if (definitions == null) {
            return Collections.emptyList();
        }
        return definitions.stream()
                .map(it -> new PublicRouteRule(it.getRoute(), it.getMethods()))
                .collect(Collectors.toList());
    }
}
