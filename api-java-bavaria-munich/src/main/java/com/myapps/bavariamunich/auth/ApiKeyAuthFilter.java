package com.myapps.bavariamunich.auth;

import com.myapps.bavariamunich.config.AppConsts;
import com.myapps.bavariamunich.config.AppProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private final List<Pattern> publicRoutePatterns;

    public ApiKeyAuthFilter(List<Pattern> publicRoutePatterns) {
        this.publicRoutePatterns = publicRoutePatterns;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (isIgnoredPath(servletPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        String providedApiKey = request.getHeader(AppConsts.API_KEY_HEADER);
        String expectedApiKey = AppProperties.getSecurityApiKey();
        if (!isValidApiKey(providedApiKey, expectedApiKey)) {
            writeUnauthorized(response);
            return;
        }
        filterChain.doFilter(request, response);

    }

    private boolean isIgnoredPath(String servletPath) {
        if (servletPath == null) {
            return false;
        }
        return publicRoutePatterns.stream()
                .anyMatch(pattern -> pattern.matcher(servletPath).matches());
    }

    private boolean isValidApiKey(String provided, String expected) {
        if (provided == null || expected == null) {
            return false;
        }
        return MessageDigest.isEqual(
                provided.getBytes(StandardCharsets.UTF_8),
                expected.getBytes(StandardCharsets.UTF_8)
        );
    }

    private void writeUnauthorized(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(AppConsts.DEFAULT_UNAUTHORIZED_RESPONSE_JSON_STR);
    }

}
