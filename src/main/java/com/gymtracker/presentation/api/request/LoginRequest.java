package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
  public String email;
  public String username;

  @NotBlank
  public String password;
}
