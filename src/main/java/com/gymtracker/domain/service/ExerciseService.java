package com.gymtracker.domain.service;

import java.util.List;

import com.gymtracker.converter.ExerciseConverter;
import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.repository.ExerciseRepository;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
}
