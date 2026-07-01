package com.gymtracker.presentation.api.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public class AddExerciseRequest {
  @NotBlank
  public UUID exerciseId;
}
