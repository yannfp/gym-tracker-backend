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
    return list("user.id = ?1 and status = ?2", id, WorkoutStatus.COMPLETED);
  }

  public WorkoutModel fetchUserActiveWorkout(UUID id) {
    return find("user.id = ?1 and status ?2", id, WorkoutStatus.IN_PROGRESS).firstResult();
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
