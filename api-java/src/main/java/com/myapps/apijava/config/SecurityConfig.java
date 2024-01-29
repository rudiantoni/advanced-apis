package com.myapps.apijava.config;

import com.myapps.apijava.auth.CustomJwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
  private final List<String> openUrls;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .cors(it -> corsConfigurationSource())
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize ->
          authorize
            .requestMatchers(openUrls.toArray(String[]::new)).permitAll()
            .anyRequest().authenticated()
//          .requestMatchers("/closed/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_TECHNICIAN")
      )
      .addFilterBefore(customJwtAuthenticationFilter, BasicAuthenticationFilter.class)
    ;
    return http.build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.allowedOrigins = List.of("*");
//    configuration.allowedMethods = List.of("*");
//    configuration.allowedHeaders = List.of("*");
    configuration.applyPermitDefaultValues();
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

}



