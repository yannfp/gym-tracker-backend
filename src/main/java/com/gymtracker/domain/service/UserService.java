package com.gymtracker.domain.service;

import java.util.UUID;

import com.gymtracker.data.model.UserModel;
import com.gymtracker.data.repository.UserRepository;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAuthorizedException;

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

  @Transactional
  public UserModel registerUser(String email, String username, String password, String firstname, String lastname) {
    if (doesUserExist(email, username)) {
      throw new BadRequestException("Email or username already in use");
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
}
