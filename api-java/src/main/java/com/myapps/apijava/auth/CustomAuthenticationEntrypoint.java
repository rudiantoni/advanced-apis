package com.myapps.apijava.auth;

import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.exception.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntrypoint implements AuthenticationEntryPoint {
  private final GlobalExceptionHandler globalExceptionHandler;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    globalExceptionHandler.handleSecurityException(response, ExceptionType.AUTHENTICATION_EXCEPTION, authException);
  }
}
