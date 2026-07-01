package com.gymtracker.domain.service;

import java.util.ArrayList;

import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.data.model.WorkoutModel;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkoutExerciseService {

  public WorkoutExerciseModel createExercise(WorkoutModel workout, ExerciseModel exercise) {
    WorkoutExerciseModel workoutExercise = new WorkoutExerciseModel();

    workoutExercise.workout = workout;
    workoutExercise.exercise = exercise;
    workoutExercise.sets = new ArrayList<>();

    return workoutExercise;
  }
}
