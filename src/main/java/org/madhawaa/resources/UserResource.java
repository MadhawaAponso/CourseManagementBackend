package org.madhawaa.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.madhawaa.dto.requestDTO.UserRequestDTO;
import org.madhawaa.dto.responseDTO.UserResponseDTO;
import org.madhawaa.enums.Role;
import org.madhawaa.security.AuthenticatedUser;
import org.madhawaa.security.UserContextService;
import org.madhawaa.service.UserService;

import java.util.List;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @Inject
    AuthenticatedUser authenticatedUser;

    @Inject
    JsonWebToken jwt;

    @Inject
    UserContextService userContextService;

    @GET
    @Path("/{id}")
    public Response getUser(@PathParam("id") Integer id) {
        try {
            UserResponseDTO user = userService.getById(id);
            return Response.ok(user).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
    @GET
    @Path("/me")
    @RolesAllowed({"student","instructor","admin"})
    public UserResponseDTO getCurrentUser() {
        Integer userId = userContextService.getUserId();
        if (userId == null) {
            throw new NotAuthorizedException("Missing or invalid token");
        }
        return userService.getById(userId);
    }


    @GET
    public Response getUsersByRole(@QueryParam("role") Role role) {
        if (role == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Query param 'role' is required")
                    .build();
        }
        List<UserResponseDTO> users = userService.getAllByRole(role);
        return Response.ok(users).build();
    }


    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        try {
            userService.deleteUser(id);
            return Response.noContent().build(); // 204 No Content
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
