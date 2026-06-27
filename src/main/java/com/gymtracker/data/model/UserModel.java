package com.gymtracker.data.model;

import java.util.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class UserModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  public UUID id;

  @Column(name = "username", nullable = false, unique = true)
  public String username;

  @Column(name = "email", nullable = false, unique = true)
  public String email;

  @Column(name = "password", nullable = false)
  public String password;

  @Column(name = "firstname", nullable = false)
  public String firstname;

  @Column(name = "lastname", nullable = false)
  public String lastname;

  @Column(name = "created_at", nullable = false, updatable = false)
  public LocalDateTime createdAt = LocalDateTime.now();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  List<WorkoutModel> workouts = new ArrayList<>();
}
