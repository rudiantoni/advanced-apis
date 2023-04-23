package com.myapps.advancedapijava.modules.auth.model;

import com.myapps.advancedapijava.modules.user.dto.UserRespDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Getter
@Setter
public class AuthenticationToken extends AbstractAuthenticationToken {
  public AuthenticationToken(UserRespDto user, Token token) {
    super(List.of(new SimpleGrantedAuthority("STANDARD_AUTHORITY")));
    setAuthenticated(true);
    this.user = user;
    this.token = token;
  }

  private UserRespDto user;
  private Token token;

  @Override
  public UserRespDto getPrincipal() {
    return getUser();
  }

  public void setPrincipal(UserRespDto user) {
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
