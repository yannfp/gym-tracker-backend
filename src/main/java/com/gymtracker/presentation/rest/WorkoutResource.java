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
import com.gymtracker.data.model.WorkoutStatus;
import com.gymtracker.domain.service.WorkoutService;
import com.gymtracker.presentation.api.request.AddExerciseRequest;
import com.gymtracker.presentation.api.response.WorkoutExerciseResponse;
import com.gymtracker.presentation.api.response.WorkoutResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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

  @POST
  @Path("/")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = WorkoutResponse.class)))
  @APIResponse(responseCode = "409", description = "Cannot start a workout you already have one in progress")
  public Response createNewWorkout() {
    UUID userId = UUID.fromString(jwt.getSubject());

    WorkoutResponse response = workoutService.createNewWorkout(userId);

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

  @PATCH
  @Path("/active/status")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "204", description = "Status updated")
  @APIResponse(responseCode = "400", description = "Need to specify a valid status")
  @APIResponse(responseCode = "404", description = "No active workout")
  public Response updateActiveStatus(@QueryParam("to") WorkoutStatus targetStatus) {
    if (targetStatus == null || targetStatus == WorkoutStatus.IN_PROGRESS) {
      throw new BadRequestException("Need to specify a valid status");
    }

    UUID userId = UUID.fromString(jwt.getSubject());

    workoutService.updateActiveStatus(userId, targetStatus);

    return Response.noContent().build();
  }

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
