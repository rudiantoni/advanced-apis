package com.myapps.apijava.auth;

import ch.qos.logback.classic.Logger;
import com.myapps.apijava.config.AppProperties;
import com.myapps.apijava.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
  private final List<String> openUrls;
  private final JwtService jwtService;

  Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Skip filter if the URL is open
    if (isRequestWithUrls(request, openUrls)) {
      filterChain.doFilter(request, response);
      return;
    }

    // Obtain, validate and parse jwt token from the request header
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String tokenPrefix = AppProperties.securityTokenPrefix;
    if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
      logger.error("Authorization header can not be null and must have the correct prefix.");
      throw new AuthenticationServiceException("Invalid authentication.");
    }
    final String jwtToken = authHeader.substring(tokenPrefix.length() + 1);
    Token token = jwtService.decodeToken(jwtToken);

    // Validate obtained parsed token
    if (token == null || token.getEmail() == null) {
      logger.error("Token could not be parsed. Check token required fields (email).");
      throw new AuthenticationServiceException("Token parse error.");
    }

    if (jwtService.isTokenValid(token)) {
      List<SimpleGrantedAuthority> grantedRoles = token.getRoles().stream()
        .map(SimpleGrantedAuthority::new)
        .toList();
      List<SimpleGrantedAuthority> grantedAuthorities = token.getAuthorities().stream()
        .map(SimpleGrantedAuthority::new)
        .toList();
      List<SimpleGrantedAuthority> userAuthorities = Stream
        .concat(grantedRoles.stream(), grantedAuthorities.stream()).toList();
      Authentication authentication = new AuthenticationToken(token, userAuthorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      var x = true;
    } else {
      logger.error("Token data could not be validated. Check user existence and required fields (username, email), check token validation fields (username, email) and expiration.");
      throw new AuthorizationServiceException("Invalid token data.");
    }
    filterChain.doFilter(request, response);
  }

  private Boolean isRequestWithUrls(HttpServletRequest request, List<String> urls) {
    return urls.stream().anyMatch(it -> new AntPathRequestMatcher(it).matches(request));
  }
}
