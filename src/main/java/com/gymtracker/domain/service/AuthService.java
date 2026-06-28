package com.gymtracker.domain.service;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.presentation.api.request.RegisterRequest;
import com.gymtracker.presentation.api.response.AuthResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class AuthService {

  @Inject
  TokenService tokenService;

  @Inject
  UserService userService;

  @Transactional
  public AuthResponse register(String email, String username, String password, String firstname, String lastname) {
    UserModel user = userService.registerUser(email, username, password, firstname, lastname);

    String accessToken = tokenService.generateAccessToken(user);
    String refreshToken = tokenService.generateRefreshToken(user);

    return new AuthResponse(accessToken, refreshToken);
  }

  public AuthResponse login(String email, String username, String password) {

    UserModel user = userService.authenticate(email, username, password);
    if (user == null) {
      throw new NotAuthorizedException("Invalid credentials");
    }

    String accessToken = tokenService.generateAccessToken(user);
    String refreshToken = tokenService.generateRefreshToken(user);

    return new AuthResponse(accessToken, refreshToken);
  }
}
