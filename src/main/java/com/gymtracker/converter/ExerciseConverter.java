package com.gymtracker.converter;

import java.util.ArrayList;
import java.util.List;

import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExerciseConverter {

  public ExerciseResponse toResponse(ExerciseModel exercise) {
    return new ExerciseResponse(
        exercise.id,
        exercise.name,
        exercise.mainMuscleGroup.toString(),
        exercise.secondaryMuscleGroup.toString());
  }

  public List<ExerciseResponse> toResponseList(List<ExerciseModel> exercises) {
    List<ExerciseResponse> response = new ArrayList<>();

    exercises.forEach(exercise -> response.add(toResponse(exercise)));

    return response;
  }
}
