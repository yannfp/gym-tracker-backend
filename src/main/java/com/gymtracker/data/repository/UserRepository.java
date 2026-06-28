package com.gymtracker.data.repository;

import com.gymtracker.data.model.UserModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserModel> {

}
