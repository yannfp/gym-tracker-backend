package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.NotBlank;

public class NewExerciseRequest {
  @NotBlank
  public String name;
  @NotBlank
  String mainMuscleGroup;
  String secondaryMuscleGroup;
}
