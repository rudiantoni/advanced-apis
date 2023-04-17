package com.myapps.advancedapijava.modules.auth.service;

import com.myapps.advancedapijava.modules.auth.JwtService;
import com.myapps.advancedapijava.modules.auth.dto.AuthReqDto;
import com.myapps.advancedapijava.modules.auth.dto.AuthResDto;
import com.myapps.advancedapijava.modules.auth.dto.RegisterReqDto;
import com.myapps.advancedapijava.modules.user.entity.User;
import com.myapps.advancedapijava.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthResDto register(RegisterReqDto request) {
    var user = User.builder()
      .email(request.getEmail())
      .username(request.getUsername())
      .password(passwordEncoder.encode(request.getPassword()))
      .build();

    userRepository.save(user);

    var jwtToken = jwtService.generateToken(user);

    return AuthResDto.builder()
      .token(jwtToken)
      .build();
  }

  public AuthResDto authenticate(AuthReqDto request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        request.getUsername(),
        request.getPassword()
      )
    );

    var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);

    return AuthResDto.builder()
      .token(jwtToken)
      .build();

  }
}
