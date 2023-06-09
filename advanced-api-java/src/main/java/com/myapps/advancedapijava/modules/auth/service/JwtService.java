package com.myapps.advancedapijava.modules.auth.service;

import com.myapps.advancedapijava.config.AppProperties;
import com.myapps.advancedapijava.modules.auth.model.Token;
import com.myapps.advancedapijava.modules.auth.model.TokenSubject;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.util.DateUtil;
import com.myapps.advancedapijava.util.Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

  public Key getSigningKey() {
    String secretKey256Hex = AppProperties.securitySecretKey;
    byte[] keyBytes = Decoders.BASE64.decode(secretKey256Hex);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  public TokenSubject encodeTokenSubject(Token token) {
    return encodeTokenSubject(token.getUsername(), token.getEmail());
  }

  public TokenSubject encodeTokenSubject(String username, String email) {
    return TokenSubject.builder()
      .username(username)
      .email(email)
      .build();
  }

  public String encodeToken(Token token) {
    TokenSubject tokenSubject = encodeTokenSubject(token);
    String subject = Util.toJsonStr(tokenSubject);

    return Jwts.builder()
      .setSubject(subject)
      .setIssuedAt(token.getIssuedAtDate())
      .setExpiration(token.getExpirationDate())
      //.setExpiration(token.getIssuedAtDate())
      .signWith(getSigningKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  public Token decodeToken(String jwtToken) {
    Claims claims = Jwts.parserBuilder()
      .setSigningKey(getSigningKey())
      .build()
      .parseClaimsJws(jwtToken)
      .getBody();

    Date issuedAtDate = claims.getIssuedAt();
    Date expirationDate = claims.getExpiration();
    String subject = claims.getSubject();
    TokenSubject tokenSubject = Util.fromJsonStr(subject, TokenSubject.class);

    return Token.builder()
      .username(tokenSubject.getUsername())
      .email(tokenSubject.getEmail())
      .issuedAtDate(issuedAtDate)
      .expirationDate(expirationDate)
      .build();
  }

  public Token createToken(User user) {
    Date issuedAtDate = new Date();
    Date expirationDate = DateUtil.plusHours(issuedAtDate, AppProperties.securityTokenExpirationHours);

    return Token.builder()
      .username(user.getUsername())
      .email(user.getEmail())
      .issuedAtDate(issuedAtDate)
      .expirationDate(expirationDate)
      .build();
  }

  public String generateToken(User user) {
    Token token = createToken(user);
    return encodeToken(token);
  }

  public String generateFullToken(User user) {
    String prefix = AppProperties.securityTokenPrefix;
    String token = generateToken(user);
    return "%s %s".formatted(prefix, token);
  }

  public Boolean isTokenExpired(Token token) {
    return token.getExpirationDate().before(new Date());
  }

  public Boolean isTokenValid(User user, Token token) {
    final Boolean isNotExpired = !isTokenExpired(token);
    final Boolean emailEquals = user.getEmail().equals(token.getEmail());
    final Boolean usernameEquals = user.getUsername().equals(token.getUsername());
    return isNotExpired && emailEquals && usernameEquals;
  }

}
