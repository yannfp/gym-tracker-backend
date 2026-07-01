package com.gymtracker.presentation.rest;

import java.util.List;
import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.domain.service.SetService;
import com.gymtracker.presentation.api.request.AddSetRequest;
import com.gymtracker.presentation.api.request.UpdateSetRequest;
import com.gymtracker.presentation.api.response.SetResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/workouts/active/exercises/{exerciseId}/sets")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SetResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  SetService setService;

  @GET
  @Path("")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SetResponse.class, type = SchemaType.ARRAY)))
  @APIResponse(responseCode = "404", description = "Exercise not found or no workout is in progress")
  public Response fetchSets(@PathParam("exerciseId") UUID exerciseId) {
    UUID userId = UUID.fromString(jwt.getSubject());

    List<SetResponse> response = setService.fetchSetsForExercise(userId, exerciseId);

    return Response.ok(response).build();
  }

  @POST
  @Path("")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SetResponse.class)))
  @APIResponse(responseCode = "404", description = "Exercise not found or no workout is in progress")
  public Response addSet(@PathParam("exerciseId") UUID exerciseId, AddSetRequest request) {
    UUID userId = UUID.fromString(jwt.getSubject());

    SetResponse response = setService.addSet(userId, exerciseId, request);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/{setId}")
  @RolesAllowed("user")
  @Transactional
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = SetResponse.class)))
  @APIResponse(responseCode = "404", description = "Set not found or workout is not active")
  public Response updateSet(@PathParam("setId") UUID setId, @Valid UpdateSetRequest request) {
    UUID userId = UUID.fromString(jwt.getSubject());

    SetResponse response = setService.updateSet(userId, setId, request);

    return Response.ok(response).build();
  }
}
