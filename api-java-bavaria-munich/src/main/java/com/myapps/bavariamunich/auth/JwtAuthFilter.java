package com.myapps.bavariamunich.auth;

import com.myapps.bavariamunich.config.AppConsts;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final List<Pattern> publicUrlPatterns;
    private final JwtService jwtService;

    public JwtAuthFilter(
            @Qualifier("publicUrlPatterns") List<Pattern> publicUrlPatterns,
            JwtService jwtService
    ) {
        this.publicUrlPatterns = publicUrlPatterns;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String servletPath = request.getServletPath();

        if (isPublicPath(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractAuthorizationToken(request.getHeader(AppConsts.AUTHORIZATION_HEADER));
        if (token == null || !jwtService.isTokenValid(token)) {
            writeUnauthorized(response);
            return;
        }

        Claims claims = jwtService.parseClaims(token);
        JwtUserDetails userDetails = jwtService.getDetailsFromClaims(claims);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getEmail(),
                null,
                Collections.emptyList()
        );
        authentication.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }

    private boolean isPublicPath(String servletPath) {
        if (servletPath == null) {
            return false;
        }
        return publicUrlPatterns.stream()
                .anyMatch(it -> it.matcher(servletPath).matches());
    }

    private String extractAuthorizationToken(String header) {
        if (header == null || !header.startsWith(AppConsts.BEARER_PREFIX)) {
            return null;
        }
        String token = header.substring(AppConsts.BEARER_PREFIX.length()).trim();
        return token.isEmpty() ? null : token;
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(AppConsts.DEFAULT_UNAUTHORIZED_RESPONSE_JSON_STR);
    }
}
