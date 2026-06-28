package com.gymtracker.presentation.rest;

import java.util.UUID;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.converter.UserConverter;
import com.gymtracker.data.model.UserModel;
import com.gymtracker.domain.service.UserService;
import com.gymtracker.presentation.api.response.UserResponse;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
  @APIResponse(responseCode = "400", description = "User not found")
  public Response me() {
    UUID userId = UUID.fromString(jwt.getSubject());

    UserModel user = userService.findById(userId);
    if (user == null) {
      throw new BadRequestException("User not found");
    }

    UserResponse response = userConverter.toUserResponse(user);

    return Response.ok(response).build();
  }
}
