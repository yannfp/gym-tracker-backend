package com.gymtracker.domain.service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.presentation.api.response.AuthResponse;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAuthorizedException;

@ApplicationScoped
public class TokenService {

  @Inject
  UserService userService;

  @Inject
  io.smallrye.jwt.auth.principal.JWTParser jwtParser;

  private static final long ACCESS_TOKEN_DURARION = 900;
  private static final long REFRESH_TOKEN_DURATION = 604800;

  public String generateAccessToken(UserModel user) {
    return Jwt.issuer("gymtracker")
        .subject(user.id.toString())
        .groups(Set.of("user"))
        .claim("email", user.email)
        .claim("username", user.username)
        .expiresIn(Duration.ofSeconds(ACCESS_TOKEN_DURARION))
        .sign();
  }

  public String generateRefreshToken(UserModel user) {
    return Jwt.issuer("gymtracker")
        .subject(user.id.toString())
        .claim("type", "refresh")
        .expiresIn(Duration.ofSeconds(REFRESH_TOKEN_DURATION))
        .sign();
  }

  public AuthResponse refreshAccessToken(String refreshToken) {
    try {
      JsonWebToken jwt = jwtParser.parse(refreshToken);

      if (!"refresh".equals(jwt.<String>getClaim("type"))) {
        throw new NotAuthorizedException("Invalid refresh token");
      }

      UserModel user = userService.findById(UUID.fromString(jwt.getSubject()));
      if (user == null) {
        throw new NotAuthorizedException("User not found");
      }
      return new AuthResponse(generateAccessToken(user), generateRefreshToken(user));
    } catch (NotAuthorizedException e) {
      throw e;
    } catch (Exception e) {
      throw new NotAuthorizedException("Invalid or expired refresh token");
    }
  }
}
