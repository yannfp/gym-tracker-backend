package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.WorkoutConverter;
import com.gymtracker.converter.WorkoutExerciseConverter;
import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.model.WorkoutStatus;
import com.gymtracker.data.repository.WorkoutRepository;
import com.gymtracker.presentation.api.response.WorkoutExerciseResponse;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class WorkoutService {

  @Inject
  WorkoutConverter workoutConverter;
  @Inject
  WorkoutExerciseConverter workoutExerciseConverter;

  @Inject
  WorkoutRepository workoutRepository;

  @Inject
  WorkoutExerciseService workoutExerciseService;
  @Inject
  ExerciseService exerciseService;

  public List<WorkoutResponse> fetchUserWorkouts(UUID userId) {
    List<WorkoutModel> workouts = workoutRepository.fetchUserWorkouts(userId);

    return workoutConverter.toResponseList(workouts);
  }

  public WorkoutModel fetchUserActiveWorkout(UUID userId) {
    return workoutRepository.fetchUserActiveWorkout(userId);
  }

  @Transactional
  public WorkoutResponse createNewWorkout(UUID userId) {
    WorkoutModel activeWorkout = fetchUserActiveWorkout(userId);
    if (activeWorkout != null) {
      throw new WebApplicationException("Cannot start a workout you already have one in progress",
          Response.Status.CONFLICT);
    }

    WorkoutModel workout = workoutRepository.createNewWorkout(userId);

    return workoutConverter.toResponse(workout);
  }

  @Transactional
  public void updateActiveStatus(UUID userId, WorkoutStatus targetStatus) {
    WorkoutModel activeWorkout = findActiveWorkout(userId);

    activeWorkout.status = targetStatus;
  }

  @Transactional
  public WorkoutExerciseResponse addExercise(UUID exerciseId, UUID userId) {
    WorkoutModel activeWorkout = findActiveWorkout(userId);

    ExerciseModel exercise = exerciseService.findByUUID(exerciseId);
    if (exercise == null) {
      throw new BadRequestException("Invalid exercise id");
    }

    WorkoutExerciseModel workoutExercise = workoutExerciseService.createExercise(activeWorkout, exercise);

    activeWorkout.exercises.add(workoutExercise);

    return workoutExerciseConverter.toResponse(workoutExercise);
  }

  @Transactional
  public void deleteWorkout(UUID workoutId) {
    Boolean deleted = workoutRepository.deleteWorkout(workoutId);
    if (!deleted) {
      throw new BadRequestException("Invalid workout id");
    }
  }

  @Transactional
  public void deleteExercise(UUID exerciseId, UUID userId) {
    findActiveWorkout(userId);

    workoutExerciseService.deleteExercise(exerciseId, userId);
  }

  private WorkoutModel findActiveWorkout(UUID userId) {
    WorkoutModel activeWorkout = fetchUserActiveWorkout(userId);
    if (activeWorkout == null) {
      throw new NotFoundException("No active workout");
    }

    return activeWorkout;
  }
}
