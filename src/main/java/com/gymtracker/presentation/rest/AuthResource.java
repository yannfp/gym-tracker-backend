package com.gymtracker.presentation.rest;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.domain.service.AuthService;
import com.gymtracker.presentation.api.request.RegisterRequest;
import com.gymtracker.presentation.api.response.AuthResponse;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

  @Inject
  AuthService authService;

  @POST
  @Path("/register")
  @APIResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
  public Response register(@Valid RegisterRequest request) {
    if (request == null) {
      return Response.status(Response.Status.BAD_REQUEST).build();
    }

    AuthResponse response = authService.register(request);

    return Response.status(Response.Status.CREATED)
        .entity(response)
        .build();
  }

}
