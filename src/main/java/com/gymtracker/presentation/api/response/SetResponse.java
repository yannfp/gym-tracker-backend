package com.gymtracker.presentation.api.response;

import java.util.UUID;

public class SetResponse {
  public UUID id;

  public Integer setNumber;
  public Integer repetitions;

  public Double weight;

  public SetResponse(UUID id, Integer setNumber, Integer repetitions, Double weight) {
    this.id = id;
    this.setNumber = setNumber;
    this.repetitions = repetitions;
    this.weight = weight;
  }
}
