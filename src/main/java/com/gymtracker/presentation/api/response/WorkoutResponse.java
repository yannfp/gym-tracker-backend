package com.gymtracker.presentation.api.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.gymtracker.data.model.WorkoutStatus;

public class WorkoutResponse {
  public UUID id;
  public String name;

  public Long duration;
  public LocalDateTime startedAt;

  public WorkoutStatus status;

  public List<WorkoutExerciseResponse> exercises;

  public WorkoutResponse(UUID id, String name, Long duration, LocalDateTime startedAt, WorkoutStatus status,
      List<WorkoutExerciseResponse> exercises) {
    this.id = id;
    this.name = name;
    this.duration = duration;
    this.startedAt = startedAt;
    this.exercises = exercises;
  }
}
