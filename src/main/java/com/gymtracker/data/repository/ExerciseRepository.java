package com.gymtracker.data.repository;

import java.util.List;
import java.util.UUID;

import com.gymtracker.data.model.ExerciseModel;
import com.gymtracker.data.model.MuscleGroup;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ExerciseRepository implements PanacheRepositoryBase<ExerciseModel, UUID> {

  public List<ExerciseModel> fetchExercises() {
    return listAll();
  }

  public ExerciseModel findByName(String name) {
    return find("name", name).firstResult();
  }

  public List<ExerciseModel> filter(String category) {
    try {
      MuscleGroup group = MuscleGroup.valueOf(category.toUpperCase());
      return list("mainMuscleGroup", group);
    } catch (IllegalArgumentException | NullPointerException e) {
      throw new NotFoundException("Muscle category not found");
    }
  }

  public ExerciseModel findByUUID(UUID exerciseId) {
    return findById(exerciseId);
  }

  @Transactional
  public void createExercise(ExerciseModel exercise) {
    persist(exercise);
  }

  @Transactional
  public Boolean deleteExercise(UUID id) {
    return deleteById(id);
  }
}
