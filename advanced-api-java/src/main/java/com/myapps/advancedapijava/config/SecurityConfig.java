package com.myapps.advancedapijava.config;


import com.myapps.advancedapijava.modules.auth.service.JwtAuthenticationFilter;
import com.myapps.advancedapijava.modules.auth.service.JwtService;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String[] allowedUrls = Util.toArray(AppProperties.nonSecuredUrlList, String[].class);
    http
      .cors()
      .and()
      .csrf().disable()
      .authorizeRequests()
      .mvcMatchers(allowedUrls).permitAll()
      .anyRequest().authenticated()
      .and()
      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()
      .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Called not implemented UserDetailsService.loadUserByUsername");
        return null;
      }
    };
  }

}
