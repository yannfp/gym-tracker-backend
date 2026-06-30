package com.gymtracker.presentation.rest;

import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.converter.UserConverter;
import com.gymtracker.domain.service.UserService;
import com.gymtracker.presentation.api.request.UpdateUserRequest;
import com.gymtracker.presentation.api.response.UserResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

  @Inject
  JsonWebToken jwt;

  @Inject
  UserService userService;

  @Inject
  UserConverter userConverter;

  @GET
  @Path("/me")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponse.class)))
  @APIResponse(responseCode = "404", description = "User not found")
  public Response me() {
    UUID userId = UUID.fromString(jwt.getSubject());

    UserResponse response = userService.findbyId(userId);

    return Response.ok(response).build();
  }

  @PATCH
  @Path("/me")
  @RolesAllowed("user")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponse.class)))
  @APIResponse(responseCode = "404", description = "User not found")
  public Response updateMe(@Valid UpdateUserRequest request) {
    UUID userId = UUID.fromString(jwt.getSubject());

    UserResponse response = userService.updateUser(userId, request.email, request.username, request.password,
        request.firstname, request.lastname);

    return Response.ok(response).build();
  }

  @DELETE
  @Path("/me")
  @RolesAllowed("user")
  @APIResponse(responseCode = "204", description = "User deleted")
  @APIResponse(responseCode = "404", description = "User not found")
  public Response deleteMe() {
    UUID userId = UUID.fromString(jwt.getSubject());

    userService.deleteUser(userId);

    return Response.noContent().build();
  }
}
