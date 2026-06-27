package com.gymtracker.data.model;

import java.util.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WorkoutModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  public UUID id;

  @Column(name = "name", nullable = false)
  public String name;

  @Column(name = "started_at", nullable = false, updatable = false)
  public LocalDateTime startedAt;

  @Column(name = "duration_seconds", nullable = false)
  public Long durationSeconds;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  public UserModel user;

  @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<WorkoutExerciseModel> exercises = new ArrayList<>();
}
