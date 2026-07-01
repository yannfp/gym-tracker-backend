package com.gymtracker.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.presentation.api.response.SetResponse;
import com.gymtracker.presentation.api.response.WorkoutExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class WorkoutExerciseConverter {

  @Inject
  SetConverter setConverter;

  public WorkoutExerciseResponse toResponse(WorkoutExerciseModel workoutExercise) {
    List<SetResponse> sets = setConverter.toResponseList(workoutExercise.sets);

    return new WorkoutExerciseResponse(
        workoutExercise.id,
        workoutExercise.exercise.id,
        workoutExercise.exercise.name,
        workoutExercise.exercise.mainMuscleGroup.toString(),
        workoutExercise.exercise.secondaryMuscleGroup.toString(),
        sets);
  }

  public List<WorkoutExerciseResponse> toResponseList(Collection<WorkoutExerciseModel> workoutExercises) {
    List<WorkoutExerciseResponse> response = new ArrayList<>();

    workoutExercises.forEach(exercise -> response.add(toResponse(exercise)));

    return response;
  }
}
