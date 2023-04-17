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
import java.util.ArrayList;
import java.util.List;

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
      .mvcMatchers("/auth/**", "/swagger-ui/**","/v3/api-docs/**")
//      .antMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**")
//      .regexMatchers("^.*auth.*$", "^.*swagger-ui.*$", "^.*v3/api-docs.*$")
//      .requestMatchers(request -> {return getCustomRequestMatchers(request);})
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

  private Boolean getCustomRequestMatchers(HttpServletRequest request) {
//    String servletPath = request.getServletPath(); // Os outros matchers analisam este caminho
    String uri = request.getRequestURI();
    List<String> regexList = new ArrayList<>();
    regexList.add("^.*auth.*$");
    regexList.add("^.*swagger-ui.*$");
    regexList.add("^.*v3/api-docs.*$");

    return regexList.stream().anyMatch(s -> {
      return uri.matches(s);
    });
  }
}
