package com.gymtracker.data.repository;

import com.gymtracker.data.model.SetModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped

public class SetRepository implements PanacheRepository<SetModel> {

  @Transactional
  public void addSet(SetModel set) {
    persist(set);
  }
}
