package com.myapps.apijava.auth;

import com.myapps.apijava.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
  private User user;
  private Token token;
//  public AuthenticationToken(User user, Token token) {
//    super(List.of(new SimpleGrantedAuthority("STANDARD_AUTHORITY")));
//    setAuthenticated(true);
//    this.user = user;
//    this.token = token;
//  }
  public AuthenticationToken(Token token) {
    super(List.of(new SimpleGrantedAuthority("STANDARD_AUTHORITY")));
    setAuthenticated(true);
    this.token = token;
  }
  @Override
  public User getPrincipal() {
    return getUser();
  }
  public void setPrincipal(User user) {
    setUser(user);
  }
  @Override
  public Token getCredentials() {
    return getToken();
  }
  public void setCredentials(Token token) {
    setToken(token);
  }
}
