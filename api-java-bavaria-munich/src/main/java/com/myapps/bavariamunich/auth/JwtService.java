package com.myapps.bavariamunich.auth;

import com.myapps.bavariamunich.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    public String generateToken(JwtUserDetails userDetails) {
        long now = System.currentTimeMillis();
        long exp = now + AppProperties.getSecurityJwtExpirationMs();

        String username = userDetails.getUsername();
        Long userId = userDetails.getUserId();
        String email = userDetails.getEmail();

        return Jwts.builder()
                .setSubject(email)
                .claim("user", username)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(exp))
                .signWith(signingKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public JwtUserDetails getDetailsFromClaims(Claims claims) {
        return new JwtUserDetails(
                claims.get("user", String.class),
                claims.get("userId", Long.class),
                claims.getSubject()
        );
    }

    private SecretKey signingKey() {
        byte[] bytes = Base64.getDecoder().decode(AppProperties.getSecurityJwtSecret().trim());
        return Keys.hmacShaKeyFor(bytes);
    }


}
