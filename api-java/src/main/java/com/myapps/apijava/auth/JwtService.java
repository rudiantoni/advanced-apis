package com.myapps.apijava.auth;

import com.myapps.apijava.config.AppProperties;
import com.myapps.apijava.entity.Permission;
import com.myapps.apijava.entity.User;
import com.myapps.apijava.enums.PermissionType;
import com.myapps.apijava.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

  @SuppressWarnings("unchecked")
  public Token decodeToken(String jwtToken) {
    Claims claims = Jwts.parser()
      .verifyWith(getSigningKey())
      .build()
      .parseSignedClaims(jwtToken)
      .getPayload();

    OffsetDateTime issuedAt = OffsetDateTime.from(claims.getIssuedAt().toInstant().atOffset(ZoneOffset.ofHours(-3)));
    OffsetDateTime expiration = OffsetDateTime.from(claims.getExpiration().toInstant().atOffset(ZoneOffset.ofHours(-3)));

    String subject = claims.getSubject();
    TokenSubject tokenSubject = Util.fromJsonStr(subject, TokenSubject.class);

    List<String> roles = (List<String>) claims.get("roles");
    List<String> authorities = (List<String>) claims.get("authorities");

    return Token.builder()
      .tokenSubject(tokenSubject)
      .issuedAt(issuedAt)
      .expiration(expiration)
      .roles(roles)
      .authorities(authorities)
      .build();
  }

  public String encodeToken(Token token) {
    String subject = Util.toJsonStr(token.getTokenSubject());

    Map<String, List<String>> customClaims = new HashMap<>();
    customClaims.put("roles", token.getRoles());
    customClaims.put("authorities", token.getAuthorities());

    return Jwts.builder()
      .subject(subject)
      .claims(customClaims)
      .issuer("MyAppsApiJava")
      .issuedAt(Date.from(token.getIssuedAt().with(ChronoField.MILLI_OF_SECOND, 0).toInstant()))
      .expiration(Date.from(token.getExpiration().with(ChronoField.MILLI_OF_SECOND, 0).toInstant()))
      .signWith(getSigningKey())
      .compact();
  }

  public String createToken(User user) {
    OffsetDateTime now = OffsetDateTime.now().with(ChronoField.MILLI_OF_SECOND, 0);
    OffsetDateTime expiration = now.plusHours(AppProperties.securityTokenExpirationHours);
    TokenSubject tokenSubject = TokenSubject.builder()
      .id(user.getId())
      .email(user.getEmail())
      .username(user.getUsername())
      .build();
    String subject = Util.toJsonStr(tokenSubject);

    List<String> roles = user.getPermissions().stream()
      .filter(p -> p.getType().equals(PermissionType.ROLE))
      .map(Permission::getName)
      .toList();

    List<String> authorities = user.getPermissions().stream()
      .filter(p -> p.getType().equals(PermissionType.AUTHORITY))
      .map(Permission::getName)
      .toList();

    Map<String, List<String>> customClaims = new HashMap<>();
    customClaims.put("roles", roles);
    customClaims.put("authorities", authorities);

    return Jwts.builder()
      .subject(subject)
      .claims(customClaims)
      .issuer("MyAppsApiJava")
      .issuedAt(Date.from(now.toInstant()))
      .expiration(Date.from(expiration.toInstant()))
      .signWith(getSigningKey())
      .compact();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(AppProperties.securitySecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public Boolean isTokenValid(Token token) {
    return token.getExpiration().isAfter(OffsetDateTime.now());
  }
}
