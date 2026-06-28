package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
  @NotBlank
  public String username;

  @Email
  @NotBlank
  public String email;

  @NotBlank
  public String password;

  @NotBlank
  public String firstname;

  @NotBlank
  public String lastname;
}
