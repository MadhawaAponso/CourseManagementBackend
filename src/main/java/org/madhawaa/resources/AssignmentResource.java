package org.madhawaa.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.AssignmentRequestDTO;
import org.madhawaa.dto.responseDTO.AssignmentResponseDTO;
import org.madhawaa.security.UserContextService;
import org.madhawaa.service.AssignmentService;

import java.util.List;

@Path("/assignments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AssignmentResource {

    @Inject
    AssignmentService assignmentService;

    @Inject
    UserContextService userContextService;

    @GET
    @Path("/lecture/{lectureId}")
    public List<AssignmentResponseDTO> getByLecture(@PathParam("lectureId") Integer lectureId) {
        return assignmentService.getByLectureId(lectureId);
    }

    @GET
    @RolesAllowed("student")
    @Path("/upcoming/student")
    public List<AssignmentResponseDTO> getUpcomingForStudent() {
        Integer studentId = userContextService.getUserId();
        System.out.println(">> JWT‑derived studentId = " + studentId);
        if (studentId == null) {
            throw new BadRequestException("Missing userId claim in JWT");
        }
        return assignmentService.getUpcomingForStudent(studentId);
    }


    @POST
    public AssignmentResponseDTO create(@Valid AssignmentRequestDTO dto) {
        return assignmentService.create(dto);
    }

    @PUT
    @Path("/{assignmentId}")
    public AssignmentResponseDTO update(@PathParam("assignmentId") Integer id, @Valid AssignmentRequestDTO dto) {
        return assignmentService.update(id, dto);
    }

    @DELETE
    @Path("/{assignmentId}")
    public Response delete(@PathParam("assignmentId") Integer id) {
        assignmentService.delete(id);
        return Response.noContent().build();
    }
    @GET
    @Path("/{assignmentId}")
    public AssignmentResponseDTO getById(@PathParam("assignmentId") Integer assignmentId) {
        return assignmentService.getById(assignmentId);
    }
}
