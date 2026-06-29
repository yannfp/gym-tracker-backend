package com.gymtracker.converter;

import java.util.ArrayList;
import java.util.List;

import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.MuscleGroup;
import com.gymtracker.presentation.api.request.NewExerciseRequest;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.BadRequestException;

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

  public ExerciseModel toModel(NewExerciseRequest exercise) {
    ExerciseModel model = new ExerciseModel();

    try {
      model.name = exercise.name;
      model.mainMuscleGroup = MuscleGroup.valueOf(exercise.mainMuscleGroup.toUpperCase());
      model.secondaryMuscleGroup = MuscleGroup.valueOf(exercise.secondaryMuscleGroup.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid muscle group");
    }

    return model;
  }
}
