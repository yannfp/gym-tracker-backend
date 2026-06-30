package com.gymtracker.converter;

import java.util.ArrayList;
import java.util.List;

import com.gymtracker.data.model.SetModel;
import com.gymtracker.presentation.api.response.SetResponse;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SetConverter {
  public SetResponse toResponse(SetModel set) {
    return new SetResponse(set.id, set.setNumber, set.repetitions, set.weight);
  }

  public List<SetResponse> toResponseList(List<SetModel> sets) {
    List<SetResponse> response = new ArrayList<>();

    sets.forEach(set -> response.add(toResponse(set)));

    return response;
  }
}
