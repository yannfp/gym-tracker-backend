package com.gymtracker.data.repository;

import com.gymtracker.data.model.SetModel;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped

public class SetRepository implements PanacheRepository<SetModel> {

}
