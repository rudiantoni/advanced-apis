package com.myapps.apijava.auth;

import com.myapps.apijava.dto.UserSecureDto;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class AuthenticationToken extends AbstractAuthenticationToken {
  private final Token token;
  private final TokenSubject tokenSubject;
  private final UserSecureDto userSecureDto;

  public AuthenticationToken(Token token, UserSecureDto userSecureDto, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
    super(simpleGrantedAuthorities);
    this.token = token;
    this.tokenSubject = token.getTokenSubject();
    this.userSecureDto = userSecureDto;
    setAuthenticated(true);
  }

  @Override
  public TokenSubject getPrincipal() {
    return tokenSubject;
  }

  @Override
  public Token getCredentials() {
    return token;
  }

  @Override
  public UserSecureDto getDetails() {
    return userSecureDto;
  }
}
