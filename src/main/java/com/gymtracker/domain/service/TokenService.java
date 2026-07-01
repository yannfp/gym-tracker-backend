package com.gymtracker.domain.service;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

import org.eclipse.microprofile.config.inject.ConfigProperty;
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

  @ConfigProperty(name = "jwt.access-token.duration", defaultValue = "900")
  long accessTokenDuration;

  @ConfigProperty(name = "jwt.refresh-token.duration", defaultValue = "604800")
  long refreshTokenDuration;

  public String generateAccessToken(UserModel user) {
    return Jwt.issuer("gymtracker")
        .subject(user.id.toString())
        .groups(Set.of("user"))
        .claim("email", user.email)
        .claim("username", user.username)
        .expiresIn(Duration.ofSeconds(accessTokenDuration))
        .sign();
  }

  public String generateRefreshToken(UserModel user) {
    return Jwt.issuer("gymtracker")
        .subject(user.id.toString())
        .claim("type", "refresh")
        .expiresIn(Duration.ofSeconds(refreshTokenDuration))
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
