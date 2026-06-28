package com.gymtracker.domain.service;

import java.util.UUID;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.data.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {
  @Inject
  UserRepository userRepository;

  public UserModel findById(UUID id) {
    return userRepository.findById(id);
  }

  public UserModel findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public UserModel findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Boolean doesUserExist(String email, String username) {
    return findByEmail(email) == null && findByUsername(username) == null;
  }
}
