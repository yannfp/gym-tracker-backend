package com.gymtracker.presentation.api.request;

import jakarta.validation.constraints.Min;

public class UpdateSetRequest {
  @Min(1)
  public Integer repetitions;

  @Min(0)
  public Double weight;
}
