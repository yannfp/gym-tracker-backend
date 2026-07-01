package com.gymtracker.data.repository;

import java.util.UUID;

import com.gymtracker.data.model.WorkoutExerciseModel;
import com.gymtracker.data.model.WorkoutStatus;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WorkoutExerciseRepository implements PanacheRepositoryBase<WorkoutExerciseModel, UUID> {

  public WorkoutExerciseModel findByUUID(UUID id) {
    return findById(id);
  }

  public WorkoutExerciseModel doesBelongToUser(UUID exerciseId, UUID userId) {
    return find("id = ?1 and workout.user.id = ?2 and workout.status = ?3", exerciseId, userId,
        WorkoutStatus.IN_PROGRESS).firstResult();
  }

  @Transactional
  public Boolean deleteExercise(UUID id) {
    return deleteById(id);
  }
}
