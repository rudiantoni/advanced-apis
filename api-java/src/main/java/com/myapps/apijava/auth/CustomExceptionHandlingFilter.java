package com.myapps.apijava.auth;

import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.exception.GlobalExceptionHandler;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomExceptionHandlingFilter extends OncePerRequestFilter {
  private final GlobalExceptionHandler globalExceptionHandler;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (AuthenticationServiceException e) {
      globalExceptionHandler.handleSecurityException(response, ExceptionType.AUTHENTICATION_EXCEPTION, e);
    } catch (AuthorizationServiceException e) {
      globalExceptionHandler.handleSecurityException(response, ExceptionType.AUTHORIZATION_EXCEPTION, e);
    }
  }
}
