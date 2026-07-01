package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.NotBlank;

public class AddSetRequest {
  @NotBlank
  public Integer repetitions;
  @NotBlank
  public Double weight;
}
