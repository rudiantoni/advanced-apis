package com.myapps.bavariamunich.config;

import com.myapps.bavariamunich.auth.ApiKeyAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfig {
    private final ApiKeyAuthFilter apiKeyAuthFilter;

    public SecurityConfig(ApiKeyAuthFilter apiKeyAuthFilter) {
        this.apiKeyAuthFilter = apiKeyAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // stateles API does not uses session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // CSRF is often disabled when using header API Key
                .csrf()
                .disable()
                // Permits all, the filter decides who passes and who gets 401
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                // Register your filter before the user/pass standard filter
                .addFilterBefore(apiKeyAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
