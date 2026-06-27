package com.gymtracker.data.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class WorkoutExerciseModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  public UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workout_id", nullable = false)
  public WorkoutModel workout;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false)
  public ExerciseModel exercise;

  @OneToMany(mappedBy = "workoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<SetModel> sets = new ArrayList<>();
}
