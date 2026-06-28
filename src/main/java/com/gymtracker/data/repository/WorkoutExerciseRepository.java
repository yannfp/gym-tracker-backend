package com.gymtracker.data.repository;

import com.gymtracker.data.model.WorkoutExerciseModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkoutExerciseRepository implements PanacheRepository<WorkoutExerciseModel> {

}
