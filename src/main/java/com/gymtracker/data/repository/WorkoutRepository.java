package com.gymtracker.data.repository;

import java.util.List;
import java.util.UUID;

import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.data.model.WorkoutStatus;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkoutRepository implements PanacheRepositoryBase<WorkoutModel, UUID> {

  public List<WorkoutModel> fetchUserWorkouts(UUID id) {
    return list("user.id", id);
  }

  public WorkoutModel fetchUserActiveWorkout(UUID id) {
    return find("user.id = ?1 and status ?2", id, WorkoutStatus.IN_PROGRESS).firstResult();
  }
}
