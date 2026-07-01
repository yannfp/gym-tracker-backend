package com.gymtracker.domain.service;

import java.util.ArrayList;
import java.util.UUID;

import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.repository.WorkoutExerciseRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class WorkoutExerciseService {

  @Inject
  WorkoutExerciseRepository workoutExerciseRepository;

  public WorkoutExerciseModel createExercise(WorkoutModel workout, ExerciseModel exercise) {
    WorkoutExerciseModel workoutExercise = new WorkoutExerciseModel();

    workoutExercise.workout = workout;
    workoutExercise.exercise = exercise;
    workoutExercise.sets = new ArrayList<>();

    return workoutExercise;
  }

  @Transactional
  public Boolean deleteExercise(UUID exerciseId, UUID userId) {
    WorkoutExerciseModel exercise = workoutExerciseRepository.doesBelongToUser(exerciseId, userId);
    if (exercise == null) {
      throw new BadRequestException("Invalid exercise id");
    }

    return workoutExerciseRepository.deleteExercise(exerciseId);
  }

  public WorkoutExerciseModel doesBelongToUser(UUID userId, UUID exerciseId) {
    return doesBelongToUser(userId, exerciseId);
  }
}
