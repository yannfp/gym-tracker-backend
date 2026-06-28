package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.Email;

public class UpdateUserRequest {
  @Email
  public String email;
  public String username;
  public String password;
  public String firstname;
  public String lastname;
}
