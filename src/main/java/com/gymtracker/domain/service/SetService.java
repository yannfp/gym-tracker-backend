package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.SetConverter;
import com.gymtracker.data.model.SetModel;
import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.presentation.api.request.AddSetRequest;
import com.gymtracker.presentation.api.response.SetResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class SetService {

  @Inject
  WorkoutExerciseService workoutExerciseService;

  @Inject
  SetConverter setConverter;

  public List<SetResponse> fetchSetsForExercise(UUID userId, UUID exerciseId) {
    WorkoutExerciseModel exercise = validateExerciseOwnership(userId, exerciseId);

    return setConverter.toResponseList(exercise.sets);
  }

  @Transactional
  public SetResponse addSet(UUID userId, UUID exerciseId, AddSetRequest request) {
    WorkoutExerciseModel exercise = validateExerciseOwnership(userId, exerciseId);

    SetModel set = new SetModel();
    set.workoutExercise = exercise;
    set.setNumber = exercise.sets.size() + 1;
    set.repetitions = 0;
    set.weight = 0.0;

    if (request.repetitions != null) {
      set.repetitions = request.repetitions;
    }

    if (request.weight != null) {
      set.weight = request.weight;
    }

    return setConverter.toResponse(set);
  }

  private WorkoutExerciseModel validateExerciseOwnership(UUID userId, UUID exerciseId) {
    WorkoutExerciseModel exercise = workoutExerciseService.doesBelongToUser(userId, exerciseId);
    if (exercise == null) {
      throw new NotFoundException("Exercise not found or no workout is in progress");
    }

    return exercise;
  }
}
