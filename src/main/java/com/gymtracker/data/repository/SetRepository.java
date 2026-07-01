package com.gymtracker.data.repository;

import java.util.UUID;

import com.gymtracker.data.model.SetModel;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped

public class SetRepository implements PanacheRepositoryBase<SetModel, UUID> {

  @Transactional
  public void addSet(SetModel set) {
    persist(set);
  }

  @Transactional
  public Boolean deleteSet(UUID setId) {
    return deleteById(setId);
  }
}
