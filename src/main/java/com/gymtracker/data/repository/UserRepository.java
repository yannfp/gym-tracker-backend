package com.gymtracker.data.repository;

import java.util.UUID;

import com.gymtracker.data.model.UserModel;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserModel, UUID> {

  public UserModel findById(UUID id) {
    return find("id", id).firstResult();
  }

  public UserModel findByEmail(String email) {
    return find("email", email).firstResult();
  }

  public UserModel findByUsername(String username) {
    return find("username", username).firstResult();
  }

  @Transactional
  public void createUser(UserModel user) {
    persist(user);
  }

  @Transactional
  public Boolean deleteUser(UUID id) {
    return deleteById(id);
  }
}
