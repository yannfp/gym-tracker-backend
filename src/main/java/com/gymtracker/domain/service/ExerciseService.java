package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.ExerciseConverter;
import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.MuscleGroup;
import com.gymtracker.data.repository.ExerciseRepository;
import com.gymtracker.presentation.api.request.NewExerciseRequest;
import com.gymtracker.presentation.api.request.UpdateExerciseRequest;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

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

  public ExerciseModel findByUUID(UUID exerciseId) {
    return exerciseRepository.findByUUID(exerciseId);
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

  @Transactional
  public ExerciseResponse addNewExercise(NewExerciseRequest newExercise) {
    ExerciseModel exercise = exerciseConverter.toModel(newExercise);

    exerciseRepository.createExercise(exercise);

    return exerciseConverter.toResponse(exercise);
  }

  @Transactional
  public ExerciseResponse updateExercise(String exerciseName, UpdateExerciseRequest newExercise) {
    ExerciseModel exercise = exerciseRepository.findByName(exerciseName);
    if (exercise == null) {
      throw new NotFoundException("Exercise not found");
    }

    if (newExercise.name != null && !newExercise.name.isBlank()) {
      if (doesExists(newExercise.name)) {
        throw new WebApplicationException("Exercise with this name already exists", Response.Status.CONFLICT);
      }

      exercise.name = newExercise.name;
    }

    try {
      if (newExercise.mainMuscleGroup != null && !newExercise.mainMuscleGroup.isBlank()) {
        exercise.mainMuscleGroup = MuscleGroup.valueOf(newExercise.mainMuscleGroup.toUpperCase());
      }

      if (newExercise.secondaryMuscleGroup != null && !newExercise.secondaryMuscleGroup.isBlank()) {
        exercise.secondaryMuscleGroup = MuscleGroup.valueOf(newExercise.secondaryMuscleGroup.toUpperCase());
      }

    } catch (IllegalArgumentException e) {
      throw new BadRequestException("Invalid muscle group");
    }

    return exerciseConverter.toResponse(exercise);
  }

  @Transactional
  public void deleteExercise(String exerciseName) {
    ExerciseModel exercise = exerciseRepository.findByName(exerciseName);
    if (exercise == null) {
      throw new NotFoundException("Exercise not found");
    }

    Boolean deleted = exerciseRepository.deleteExercise(exercise.id);
    if (!deleted) {
      throw new NotFoundException("Exercise not found");
    }
  }

  private Boolean doesExists(String exerciseName) {
    return exerciseRepository.findByName(exerciseName) != null;
  }
}
