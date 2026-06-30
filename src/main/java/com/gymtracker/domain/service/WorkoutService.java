package com.gymtracker.domain.service;

import java.util.List;
import java.util.UUID;

import com.gymtracker.converter.WorkoutConverter;
import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.repository.WorkoutRepository;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

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
}
