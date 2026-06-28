package com.gymtracker.presentation.api.response;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {
  public UUID id;
  public String email;
  public String username;
  public String firstname;
  public String lastname;
  public LocalDateTime createdAt;

  public UserResponse(UUID id, String email, String username, String firstname, String lastname,
      LocalDateTime createdAt) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.firstname = firstname;
    this.lastname = lastname;
    this.createdAt = createdAt;
  }
}
