package com.gymtracker.presentation.api.response;

import java.util.List;
import java.util.UUID;

public class WorkoutExerciseResponse {
  public UUID id;

  public UUID exerciseId;
  public String exerciseName;
  public String mainMuscleGroup;
  public String secondaryMuscleGroup;

  public List<SetResponse> sets;

  public WorkoutExerciseResponse(UUID id, UUID exerciseId, String exerciseName, String mainMuscleGroup,
      String secondaryMuscleGroup, List<SetResponse> sets) {
    this.id = id;

    this.exerciseId = exerciseId;
    this.exerciseName = exerciseName;
    this.mainMuscleGroup = mainMuscleGroup;
    this.secondaryMuscleGroup = secondaryMuscleGroup;

    this.sets = sets;
  }
}
