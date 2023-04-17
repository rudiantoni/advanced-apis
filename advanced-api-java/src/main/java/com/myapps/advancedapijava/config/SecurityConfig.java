package com.myapps.advancedapijava.config;

import com.myapps.advancedapijava.modules.auth.JwtAuthenticationFilter;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeHttpRequests()
      .requestMatchers(request -> request.getRequestURI().matches("^.*/api/auth.*$"))
      .permitAll() // Permit all do anterior
      .anyRequest()
      .authenticated() // Outras requestes precisam ser autorizadas
      .and()
      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Não é para armazenar os dados de autenticação
      .and()
      .authenticationProvider(authenticationProvider)
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


    return http.build();
  }
}
