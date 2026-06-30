package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.WorkoutConverter;
import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.repository.WorkoutRepository;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class WorkoutService {

  @Inject
  WorkoutConverter workoutConverter;

  @Inject
  WorkoutRepository workoutRepository;

  public List<WorkoutResponse> fetchUserWorkouts(UUID id) {
    List<WorkoutModel> workouts = workoutRepository.fetchUserWorkouts(id);

    return workoutConverter.toResponseList(workouts);
  }

  public WorkoutModel fetchUserActiveWorkout(UUID id) {
    return workoutRepository.fetchUserActiveWorkout(id);
  }

  @Transactional
  public WorkoutResponse createNewWorkout(UUID id) {

    WorkoutModel activeWorkout = fetchUserActiveWorkout(id);
    if (activeWorkout != null) {
      throw new WebApplicationException("Cannot start a workout you already have one in progress",
          Response.Status.CONFLICT);
    }

    WorkoutModel workout = workoutRepository.createNewWorkout(id);

    return workoutConverter.toResponse(workout);
  }
}
