package org.madhawaa.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.madhawaa.security.AuthenticatedUser;

@Path("/auth-test")
public class AuthTestResource {

    @Inject
    AuthenticatedUser user;

    @GET
    @Path("/admin")
    @RolesAllowed("admin")
    public Response adminOnly() {
        return Response.ok("Hello Admin: " + user.getUsername()).build();
    }
    @GET
    @Path("/instructor")
    @RolesAllowed("instructor")
    public Response instructorOnly() {
        return Response.ok("Hello instructor: " + user.getUsername()).build();
    }

    @GET
    @Path("/student")
    @RolesAllowed("student")
    public Response studentOnly() {
        return Response.ok("Hello Student: " + user.getUsername()).build();
    }

    @GET
    @Path("/me")
    public Response currentUser() {
        return Response.ok("Logged in as: " + user.getUsername() + " | Roles: " + user.getRoles()).build();
    }
}
