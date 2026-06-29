package com.gymtracker.presentation.api.response;

import java.util.UUID;

public class ExerciseResponse {
  public UUID id;
  public String name;
  public String mainMuscleGroup;
  public String secondaryMuscleGroup;

  public ExerciseResponse(UUID id, String name, String mainMuscleGroup, String secondaryMuscleGroup) {
    this.id = id;
    this.name = name;
    this.mainMuscleGroup = mainMuscleGroup;
    this.secondaryMuscleGroup = secondaryMuscleGroup;
  }
}
