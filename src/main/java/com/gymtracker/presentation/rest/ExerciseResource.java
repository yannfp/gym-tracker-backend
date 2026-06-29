package com.gymtracker.presentation.rest;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.domain.service.ExerciseService;
import com.gymtracker.presentation.api.response.ExerciseResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/exercises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseResource {

  @Inject
  ExerciseService exerciseService;

  @GET
  @Path("/")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ExerciseResponse.class, type = SchemaType.ARRAY)))
  public Response fetchExercises() {
    List<ExerciseResponse> response = exerciseService.fetchExercises();

    return Response.ok(response).build();
  }

  @GET
  @Path("/{name}")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = ExerciseResponse.class)))
  @APIResponse(responseCode = "404", description = "Exercise not found")
  public Response fetchExercise(@PathParam("name") String exerciseName) {
    ExerciseResponse response = exerciseService.findExerciseByName(exerciseName);

    return Response.ok(response).build();
  }
}
