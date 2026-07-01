package com.gymtracker.data.repository;

import java.util.List;
import java.util.UUID;

import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.model.WorkoutStatus;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class WorkoutRepository implements PanacheRepositoryBase<WorkoutModel, UUID> {

  @Inject
  UserRepository userRepository;

  public List<WorkoutModel> fetchUserWorkouts(UUID id) {
    return find("SELECT DISTINCT w FROM WorkoutModel w " +
        "LEFT JOIN FETCH w.exercises e " +
        "LEFT JOIN FETCH e.sets " +
        "WHERE w.user.id = ?1 AND w.status = ?2", id, WorkoutStatus.COMPLETED).list();
  }

  public WorkoutModel fetchUserActiveWorkout(UUID id) {
    return find("SELECT DISTINCT w FROM WorkoutModel w " +
        "LEFT JOIN FETCH w.exercises e " +
        "LEFT JOIN FETCH e.sets " +
        "WHERE w.user.id = ?1 AND w.status = ?2", id, WorkoutStatus.IN_PROGRESS).firstResult();
  }

  @Transactional
  public void createWorkout(WorkoutModel workout) {
    persist(workout);
  }

  @Transactional
  public Boolean deleteWorkout(UUID workoutId) {
    return deleteById(workoutId);
  }
}
