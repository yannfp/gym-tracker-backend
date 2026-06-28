package com.gymtracker.converter;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.presentation.api.response.UserResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserConverter {
  public UserResponse toUserResponse(UserModel user) {
    return new UserResponse(
        user.id,
        user.email,
        user.username,
        user.lastname,
        user.firstname,
        user.createdAt);
  }
}
