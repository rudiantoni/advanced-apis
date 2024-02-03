package com.myapps.apijava.config;

import com.myapps.apijava.auth.CustomAccessDeniedHandler;
import com.myapps.apijava.auth.CustomAuthenticationEntrypoint;
import com.myapps.apijava.auth.CustomExceptionHandlingFilter;
import com.myapps.apijava.auth.CustomJwtAuthenticationFilter;
import com.myapps.apijava.enums.PermissionData;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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
  private final CustomAuthenticationEntrypoint customAuthenticationEntrypoint;
  private final CustomAccessDeniedHandler customAccessDeniedHandler;
  private final CustomExceptionHandlingFilter customExceptionHandlingFilter;
  private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;
  private final List<String> openUrls;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .exceptionHandling(configurer -> {
        configurer.accessDeniedHandler(customAccessDeniedHandler);
        configurer.authenticationEntryPoint(customAuthenticationEntrypoint);
      })
      .cors(it -> corsConfigurationSource())
      .csrf(AbstractHttpConfigurer::disable)
      .addFilterBefore(customExceptionHandlingFilter, BasicAuthenticationFilter.class)
      .addFilterAfter(customJwtAuthenticationFilter, CustomExceptionHandlingFilter.class)
      .authorizeHttpRequests(authorize ->
          authorize
            .requestMatchers(openUrls.toArray(String[]::new)).permitAll()
//          .requestMatchers("/closed/**").permitAll() // APENAS PARA TESTES
//          .requestMatchers("/closed/**").hasAnyAuthority(
//            PermissionName.AUTHORITY_ACCESS_ALL_USER_DATA.getName(),
//            PermissionName.AUTHORITY_MODIFY_SYSTEM_SETTINGS.getName(),
//            PermissionName.AUTHORITY_VIEW_TEAM_REPORTS.getName()
//          )
            .requestMatchers("/closed/**").hasAnyRole(
              PermissionData.ROLE_ADMIN.getName()
            )
            .anyRequest().authenticated()
      )
      .sessionManagement(httpSecuritySessionManagementConfigurer ->
        httpSecuritySessionManagementConfigurer
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );
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



