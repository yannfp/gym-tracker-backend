package com.gymtracker.data.repository;

import com.gymtracker.data.model.WorkoutModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkoutRepository implements PanacheRepository<WorkoutModel> {

}
