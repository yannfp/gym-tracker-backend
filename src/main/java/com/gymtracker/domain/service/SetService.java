package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.SetConverter;
import com.gymtracker.data.model.SetModel;
import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.data.model.WorkoutStatus;
import com.gymtracker.data.repository.SetRepository;
import com.gymtracker.presentation.api.request.AddSetRequest;
import com.gymtracker.presentation.api.request.UpdateSetRequest;
import com.gymtracker.presentation.api.response.SetResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class SetService {

  @Inject
  WorkoutExerciseService workoutExerciseService;

  @Inject
  SetConverter setConverter;

  @Inject
  SetRepository setRepository;

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

  @Transactional
  public SetResponse updateSet(UUID userId, UUID setId, UpdateSetRequest request) {
    SetModel set = findActiveSet(userId, setId);

    if (request.repetitions != null) {
      set.repetitions = request.repetitions;
    }

    if (request.weight != null) {
      set.weight = request.weight;
    }

    return setConverter.toResponse(set);
  }

  @Transactional
  public void deleteSet(UUID userId, UUID setId) {
    findActiveSet(userId, setId);

    Boolean deleted = setRepository.deleteSet(setId);
    if (!deleted) {
      throw new NotFoundException("Exercise not found or no workout is in progress");
    }
  }

  private WorkoutExerciseModel validateExerciseOwnership(UUID userId, UUID exerciseId) {
    WorkoutExerciseModel exercise = workoutExerciseService.doesBelongToUser(userId, exerciseId);
    if (exercise == null) {
      throw new NotFoundException("Exercise not found or no workout is in progress");
    }

    return exercise;
  }

  private SetModel findActiveSet(UUID userId, UUID setId) {
    SetModel set = setRepository.findById(setId);
    if (set == null || !set.workoutExercise.workout.user.id.equals(userId)
        || set.workoutExercise.workout.status != WorkoutStatus.IN_PROGRESS) {
      throw new BadRequestException("Set not found or workout is not active");
    }

    return set;
  }
}
