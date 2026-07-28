package com.myapps.bavariamunich.auth;

import com.myapps.bavariamunich.config.AppConsts;
import com.myapps.bavariamunich.dto.ErrorResponseDto;
import com.myapps.bavariamunich.util.JsonUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final List<PublicRouteRule> publicRouteRules;
    private final JwtService jwtService;

    public JwtAuthFilter(
            @Qualifier("publicRouteRules") List<PublicRouteRule> publicRouteRules,
            JwtService jwtService
    ) {
        this.publicRouteRules = publicRouteRules;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        if (isPublicRequest(request)) {
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

    private boolean isPublicRequest(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String method = request.getMethod();
        return publicRouteRules.stream()
                .anyMatch(it -> it.matches(servletPath, method));
    }

    private String extractAuthorizationToken(String header) {
        if (header == null || !header.startsWith(AppConsts.BEARER_PREFIX)) {
            return null;
        }
        String token = header.substring(AppConsts.BEARER_PREFIX.length()).trim();
        return token.isEmpty() ? null : token;
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        String responseBody = Objects.requireNonNull(
                JsonUtil.toJsonStr(ErrorResponseDto.of(HttpStatus.UNAUTHORIZED.getReasonPhrase()))
        );
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseBody);
    }
}
