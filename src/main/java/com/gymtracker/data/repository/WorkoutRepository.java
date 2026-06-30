package com.gymtracker.data.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.gymtracker.data.model.UserModel;
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
  public WorkoutModel createNewWorkout(UUID id) {
    UserModel userProxy = userRepository.getEntityManager().getReference(UserModel.class, id);

    WorkoutModel workout = new WorkoutModel();

    int hour = workout.startedAt.getHour();

    if (hour <= 12) {
      workout.name = "Morning workout";
    } else if (hour > 12 && hour <= 18) {
      workout.name = "Afternoon workout";
    } else {
      workout.name = "Evening workout";
    }

    workout.durationSeconds = null;
    workout.user = userProxy;
    workout.exercises = new ArrayList<>();

    persist(workout);

    return workout;
  }
}
