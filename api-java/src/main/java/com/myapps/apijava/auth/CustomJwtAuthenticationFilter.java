package com.myapps.apijava.auth;

import ch.qos.logback.classic.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
  private final List<String> openUrls;
  private final JwtService jwtService;

  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    if (isRequestWithUrls(request, openUrls)) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String tokenPrefix = "Bearer";

    if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
      logger.error("Authorization header can not be null and must have the correct prefix.");
      throw new AuthenticationServiceException("Invalid authentication.");
    }

    final String jwtToken = authHeader.substring(tokenPrefix.length() + 1);
//    Token token = jwtService.decodeToken(jwtToken);

    filterChain.doFilter(request, response);

  }

  private Boolean isRequestWithUrls(HttpServletRequest request, List<String> urls) {
    return urls.stream().anyMatch(it -> new AntPathRequestMatcher(it).matches(request));
  }
}
