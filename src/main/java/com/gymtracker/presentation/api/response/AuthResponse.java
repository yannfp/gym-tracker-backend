package com.gymtracker.presentation.api.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthResponse {
  public String accessToken;
  public String refreshToken;
}
