package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.NotBlank;

public class NewExerciseRequest {
  @NotBlank
  public String name;
  @NotBlank
  public String mainMuscleGroup;
  public String secondaryMuscleGroup;
}
