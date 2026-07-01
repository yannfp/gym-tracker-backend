package com.gymtracker.presentation.rest;

import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.domain.service.WorkoutExerciseService;
import com.gymtracker.domain.service.WorkoutService;
import com.gymtracker.presentation.api.request.AddExerciseRequest;
import com.gymtracker.presentation.api.response.WorkoutExerciseResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/workouts/active/exercises")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutExerciseResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  WorkoutExerciseService workoutExerciseService;

  @Inject
  WorkoutService workoutService;

  @POST
  @Path("/active/exercises")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = WorkoutExerciseResponse.class)))
  @APIResponse(responseCode = "400", description = "Invalid exercise id")
  @APIResponse(responseCode = "404", description = "No active workout")
  public Response addExercise(@Valid AddExerciseRequest request) {
    UUID userId = UUID.fromString(jwt.getSubject());

    WorkoutExerciseResponse response = workoutService.addExercise(request.exerciseId, userId);

    return Response.ok(response).build();
  }

  @DELETE
  @Path("/active/exercises/{id}")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "204", description = "Exercise deleted")
  @APIResponse(responseCode = "400", description = "Invalid exercise id")
  @APIResponse(responseCode = "404", description = "No active workout")
  public Response deleteExercise(@PathParam("id") UUID exerciseId) {
    UUID userId = UUID.fromString(jwt.getSubject());

    workoutService.deleteExercise(exerciseId, userId);

    return Response.noContent().build();
  }
}
