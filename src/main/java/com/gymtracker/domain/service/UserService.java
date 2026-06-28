package com.gymtracker.domain.service;

import java.util.UUID;

import com.gymtracker.converter.UserConverter;
import com.gymtracker.data.model.UserModel;
import com.gymtracker.data.repository.UserRepository;
import com.gymtracker.presentation.api.response.UserResponse;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserService {
  @Inject
  UserRepository userRepository;

  @Inject
  UserConverter userConverter;

  public UserModel findById(UUID id) {
    return userRepository.findById(id);
  }

  public UserResponse findbyId(UUID id) {
    UserModel user = findById(id);
    if (user == null) {
      throw new BadRequestException("User not found");
    }

    return userConverter.toUserResponse(user);
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

  @Transactional
  public UserModel registerUser(String email, String username, String password, String firstname, String lastname) {
    if (doesUserExist(email, username)) {
      throw new WebApplicationException("Email or username already in use", Response.Status.CONFLICT);
    }

    UserModel user = new UserModel();

    user.email = email;
    user.username = username;
    user.password = BcryptUtil.bcryptHash(password);
    user.firstname = firstname;
    user.lastname = lastname;

    userRepository.createUser(user);

    return user;
  }

  public UserModel authenticate(String email, String username, String password) {
    if (!doesUserExist(email, username)) {
      throw new NotAuthorizedException("Invalid credentials");
    }

    UserModel user = findByEmail(email);
    if (user == null) {
      user = findByUsername(username);
    }

    if (!BcryptUtil.matches(password, user.password)) {
      throw new NotAuthorizedException("Invalid credentials");
    }

    return user;
  }

  @Transactional
  public UserResponse updateUser(UUID userId, String newEmail, String newUsername, String newPassword,
      String newFirstname, String newLastname) {

    UserModel user = findById(userId);
    if (user == null) {
      throw new BadRequestException("User not found");
    }

    if (newEmail != null && !newEmail.isBlank()) {
      user.email = newEmail;
    }

    if (newUsername != null && !newUsername.isBlank()) {
      user.username = newUsername;
    }

    if (newPassword != null && !newPassword.isBlank()) {
      user.password = BcryptUtil.bcryptHash(newPassword);
    }

    if (newFirstname != null && !newFirstname.isBlank()) {
      user.firstname = newFirstname;
    }

    if (newLastname != null && !newLastname.isBlank()) {
      user.lastname = newLastname;
    }

    return userConverter.toUserResponse(user);
  }
}
