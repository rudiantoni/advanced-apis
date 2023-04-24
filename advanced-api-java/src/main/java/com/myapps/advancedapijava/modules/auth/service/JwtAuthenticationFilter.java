package com.myapps.advancedapijava.modules.auth.service;

import ch.qos.logback.classic.Logger;
import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.modules.auth.model.AuthenticationToken;
import com.myapps.advancedapijava.modules.auth.model.Token;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.service.UserService;
import com.myapps.advancedapijava.modules.user.util.UserUtil;
import com.myapps.advancedapijava.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserService userService;
  Logger logger = Util.getLogger(this.getClass());

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      if (isRequestedAlreadyPermitted(request)) {
        filterChain.doFilter(request, response);
        return;
      }

      final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      final String tokenPrefix = AppProperties.securityTokenPrefix;

      if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
        logger.error("Authorization header can not be null and must have the correct prefix.");
        throw new AuthenticationServiceException("Invalid authorization.");
      }

      final String jwtToken = authHeader.substring(tokenPrefix.length() + 1);
      Token token = jwtService.decodeToken(jwtToken);

      if (token == null || token.getEmail() == null || token.getUsername() == null) {
        logger.error("Token could not be parsed. Check token required field (username, email).");
        throw new AuthenticationServiceException("Token parse error.");
      }

      final String email = token.getEmail();
      final String username = token.getUsername();
      User user = userService.findByEmailOrUsernameOrNull(email, username);

      if (jwtService.isTokenValid(user, token)) {
        Authentication authentication = getAuthentication(user, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        logger.error("Token data could not be validated. Check user existence and required fields (username, email), check token validation fields (username, email) and expiration.");
        throw new AuthorizationServiceException("Invalid token data.");
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      setResponseData(e, response);
    }
  }

  public void setResponseData(Exception e, HttpServletResponse response) throws IOException {
    logger.error("Error: %s".formatted(e.getMessage()));
    // Status padrão e também para AuthenticationServiceException
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    if (e instanceof AuthorizationServiceException) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
    }
    response.getWriter().write(e.getMessage());
  }

  private Boolean isRequestedAlreadyPermitted(HttpServletRequest request) {
    List<String> nonSecuredUrls = AppProperties.nonSecuredUrlList;
    return nonSecuredUrls.stream().anyMatch(it -> new AntPathRequestMatcher(it).matches(request));
  }

  private Authentication getAuthentication(User user, Token token) {
    return new AuthenticationToken(UserUtil.toRespDto(user), token);
  }

}
