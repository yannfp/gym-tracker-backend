package com.gymtracker.domain.service;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.presentation.api.request.RegisterRequest;
import com.gymtracker.presentation.api.response.AuthResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class AuthService {

  @Inject
  TokenService tokenService;

  @Inject
  UserService userService;

  @Transactional
  public AuthResponse register(RegisterRequest request) {

    if (userService.doesUserExist(request.email, request.username)) {
      throw new BadRequestException("Email or username already in use");
    }

    UserModel user = userService.createUser(request.email, request.username, request.password, request.firstname,
        request.lastname);

    String accessToken = tokenService.generateAccessToken(user);
    String refreshToken = tokenService.generateRefreshToken(user);

    return new AuthResponse(accessToken, refreshToken);
  }
}
