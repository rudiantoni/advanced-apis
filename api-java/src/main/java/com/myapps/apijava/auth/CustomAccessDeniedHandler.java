package com.myapps.apijava.auth;

import com.myapps.apijava.enums.ExceptionType;
import com.myapps.apijava.exception.GlobalExceptionHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  private final GlobalExceptionHandler globalExceptionHandler;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    globalExceptionHandler.handleSecurityException(response, ExceptionType.AUTHORIZATION_EXCEPTION, accessDeniedException);
  }
}
