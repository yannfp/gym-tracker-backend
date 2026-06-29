package com.gymtracker.data.repository;

import java.util.List;

import com.gymtracker.data.model.ExerciseModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExerciseRepository implements PanacheRepository<ExerciseModel> {

  public List<ExerciseModel> fetchExercises() {
    return listAll();
  }
}
