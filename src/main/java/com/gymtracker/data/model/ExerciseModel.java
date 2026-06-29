package com.gymtracker.data.model;

import java.util.*;
import jakarta.persistence.*;

@Entity
public class ExerciseModel {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  public UUID id;

  @Column(name = "name", nullable = false, unique = true)
  public String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "main_muscle_group", nullable = false)
  public MuscleGroup mainMuscleGroup;

  @Enumerated(EnumType.STRING)
  @Column(name = "secondary_muscle_group")
  public MuscleGroup secondaryMuscleGroup;
}
