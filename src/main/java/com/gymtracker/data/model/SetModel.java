package com.gymtracker.data.model;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
public class SetModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  public UUID id;

  @Column(name = "set_number", nullable = false)
  public Integer setNumber;

  @Column(name = "repetitions", nullable = false)
  public Integer repetitions;

  @Column(name = "weight", nullable = false)
  public Double weight;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workout_exercise_id", nullable = false)
  public WorkoutExerciseModel workoutExercise;
}
