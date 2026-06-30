package com.gymtracker.presentation.rest;

import java.util.UUID;
import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.converter.WorkoutConverter;
import com.gymtracker.data.model.WorkoutModel;
import com.gymtracker.domain.service.WorkoutService;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/workouts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkoutResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  WorkoutService workoutService;

  @Inject
  WorkoutConverter workoutConverter;

  @GET
  @Path("/")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WorkoutResponse.class, type = SchemaType.ARRAY)))
  public Response fetchUserWorkouts() {
    UUID userId = UUID.fromString(jwt.getSubject());

    List<WorkoutResponse> response = workoutService.fetchUserWorkouts(userId);

    return Response.ok(response).build();
  }

  @GET
  @Path("/active")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = WorkoutResponse.class)))
  @APIResponse(responseCode = "204", description = "No active workout in progress")
  public Response fetchActiveWorkout() {
    UUID userId = UUID.fromString(jwt.getSubject());

    WorkoutModel workout = workoutService.fetchUserActiveWorkout(userId);
    if (workout == null) {
      return Response.noContent().build();
    }

    WorkoutResponse response = workoutConverter.toResponse(workout);

    return Response.ok(response).build();
  }

  @POST
  @Path("/")
  @RolesAllowed("user")
  @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = WorkoutResponse.class)))
  @APIResponse(responseCode = "409", description = "Cannot start a workout you already have one in progress")
  public Response createNewWorkout() {
    UUID userId = UUID.fromString(jwt.getSubject());

    WorkoutResponse response = workoutService.createNewWorkout(userId);

    return Response.ok(response).build();
  }
}
