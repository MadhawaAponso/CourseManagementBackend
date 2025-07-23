package org.madhawaa.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.CourseFeedbackRequestDTO;
import org.madhawaa.dto.responseDTO.CourseFeedbackResponseDTO;
import org.madhawaa.security.UserContextService;
import org.madhawaa.service.CourseFeedbackService;

import java.util.List;

@Path("/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseFeedbackResource {

    @Inject CourseFeedbackService service;
    @Inject UserContextService userContext;

    // Instructors only: view all feedback for a course
    @GET
    @Path("/course/{courseId}")
    @RolesAllowed("instructor")
    public List<CourseFeedbackResponseDTO> getFeedbacksByCourse(
            @PathParam("courseId") Integer courseId) {
        return service.getFeedbacksByCourse(courseId);
    }

    // Students only: view their own feedback
    @GET
    @Path("/course/{courseId}/mine")
    @RolesAllowed("student")
    public CourseFeedbackResponseDTO getMyFeedback(
            @PathParam("courseId") Integer courseId) {
        Integer studentId = userContext.getUserId();
        return service.getMyFeedback(courseId, studentId);
    }

    // Students only: submit feedback once
    @POST
    @Path("/course/{courseId}")
    @Transactional
    @RolesAllowed("student")
    public Response submit(
            @PathParam("courseId") Integer courseId,
            @Valid CourseFeedbackRequestDTO dto) {
        Integer studentId = userContext.getUserId();
        service.submitFeedback(courseId, studentId, dto);
        return Response.status(Response.Status.CREATED).build();
    }
}
