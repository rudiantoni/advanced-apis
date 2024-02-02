package com.myapps.apijava.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
  private Token token;

  public AuthenticationToken(Token token, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
    super(simpleGrantedAuthorities);
    setAuthenticated(true);
    this.token = token;
  }

  @Override
  public Token getPrincipal() {
    return getToken();
  }

  public void setPrincipal(Token user) {
    setToken(user);
  }

  @Override
  public Token getCredentials() {
    return getToken();
  }

  public void setCredentials(Token token) {
    setToken(token);
  }
}
