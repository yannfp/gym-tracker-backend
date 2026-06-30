package com.gymtracker.converter;

import java.util.List;

import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.presentation.api.response.WorkoutExerciseResponse;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkoutConverter {

  @Inject
  WorkoutExerciseConverter workoutExerciseConverter;

  public WorkoutResponse toResponse(WorkoutModel workout) {
    List<WorkoutExerciseResponse> workoutExercise = workoutExerciseConverter.toResponseList(workout.exercises);

    return new WorkoutResponse(
        workout.id,
        workout.name,
        workout.durationSeconds,
        workout.startedAt,
        workout.status,
        workoutExercise);
  }
}
