package com.myapps.advancedapijava.modules.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");
    final String jwtToken;
    final String username;
    final String email;

    if (authHeader == null || !authHeader.startsWith("Bearer")) {
      filterChain.doFilter(request, response);
      return;
    }

    jwtToken = authHeader.substring(7);
    username = jwtService.extractUsername(jwtToken);

    // Usuário ainda não está autenticado
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

      // Localizar usuário do banco de dados
      UserDetails user = this.userDetailsService.loadUserByUsername(username);

      // Verificar tokem é válido
      if (jwtService.isTokenValid(jwtToken, user)) {
        // Cria o objeto para o contexto
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          user, null, user.getAuthorities()
        );
        // Adiciona detalhes
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // Atualizar o contexto de segurança: Autenticação válida
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }

      filterChain.doFilter(request, response);

    }
  }
}
