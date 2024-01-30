package com.myapps.apijava.auth;

import com.myapps.apijava.config.AppProperties;
import com.myapps.apijava.entity.User;
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

@Service
public class JwtService {

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

    return Token.builder()
      .username(tokenSubject.getUsername())
      .email(tokenSubject.getEmail())
      .issuedAt(issuedAt)
      .expiration(expiration)
      .build();
  }

  public String encodeToken(Token token) {
    TokenSubject tokenSubject = TokenSubject.builder()
      .username(token.getUsername())
      .email(token.getEmail())
      .build();
    String subject = Util.toJsonStr(tokenSubject);

    return Jwts.builder()
      .subject(subject)
      .issuer("MyAppsApiJava")
      .subject(subject)
      .issuedAt(Date.from(token.getIssuedAt().with(ChronoField.MILLI_OF_SECOND, 0).toInstant()))
      .expiration(Date.from(token.getExpiration().with(ChronoField.MILLI_OF_SECOND, 0).toInstant()))
      .signWith(getSigningKey())
      .compact();
    /**
     * Caso necessite adicionar claims personalizadas:
     *
     * Map<String, Object> customClaims = new HashMap<>();
     * customClaims.put("customClaimKey", "customClaimValue");
     * Jwts.builder().claims(customClaims).build()
     */
  }

  public String createToken(User user) {
    TokenSubject tokenSubject = TokenSubject.builder()
      .username(user.getUsername())
      .email(user.getEmail())
      .build();
    String subject = Util.toJsonStr(tokenSubject);

    OffsetDateTime now = OffsetDateTime.now().with(ChronoField.MILLI_OF_SECOND, 0);
    OffsetDateTime expiration = now.plusHours(AppProperties.securityTokenExpirationHours);

    return Jwts.builder()
      .subject(subject)
      .issuer("MyAppsApiJava")
      .subject(subject)
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
