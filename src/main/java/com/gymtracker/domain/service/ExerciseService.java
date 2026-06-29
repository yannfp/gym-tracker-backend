package com.gymtracker.domain.service;

import java.util.List;

import com.gymtracker.converter.ExerciseConverter;
import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.repository.ExerciseRepository;
import com.gymtracker.presentation.api.request.NewExerciseRequest;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ExerciseService {

  @Inject
  ExerciseRepository exerciseRepository;

  @Inject
  ExerciseConverter exerciseConverter;

  public List<ExerciseResponse> fetchExercises() {
    List<ExerciseModel> exercises = exerciseRepository.fetchExercises();

    return exerciseConverter.toResponseList(exercises);
  }

  public ExerciseResponse findExerciseByName(String exerciseName) {
    ExerciseModel exercise = exerciseRepository.findByName(exerciseName);
    if (exercise == null) {
      throw new NotFoundException("Exercise not found");
    }

    return exerciseConverter.toResponse(exercise);
  }

  public List<ExerciseResponse> filterExercises(String category) {
    List<ExerciseModel> exercises = exerciseRepository.filter(category);

    return exerciseConverter.toResponseList(exercises);
  }

  public ExerciseResponse addNewExercise(NewExerciseRequest newExercise) {
    ExerciseModel exercise = exerciseConverter.toModel(newExercise);

    exerciseRepository.createExercise(exercise);

    return exerciseConverter.toResponse(exercise);
  }
}
