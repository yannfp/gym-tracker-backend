package com.gymtracker.presentation.rest;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import com.gymtracker.domain.service.AuthService;
import com.gymtracker.presentation.api.request.LoginRequest;
import com.gymtracker.presentation.api.request.RegisterRequest;
import com.gymtracker.presentation.api.response.AuthResponse;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
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
      throw new BadRequestException("The body of the request is empty");
    }

    AuthResponse response = authService.register(request.email, request.username, request.password, request.firstname,
        request.lastname);

    return Response.status(Response.Status.CREATED)
        .entity(response)
        .build();
  }

  @POST
  @Path("login")
  @APIResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AuthResponse.class)))
  public Response login(@Valid LoginRequest request) {
    if (request == null) {
      throw new BadRequestException("The body of the request is empty");
    }

    if (request.email == null && request.username == null) {
      throw new BadRequestException("Email or username is required");
    }

    AuthResponse response = authService.login(request.email, request.username, request.password);

    return Response.ok(response).build();
  }
}
