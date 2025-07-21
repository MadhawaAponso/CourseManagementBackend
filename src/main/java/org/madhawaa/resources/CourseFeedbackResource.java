package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.CourseFeedbackRequestDTO;
import org.madhawaa.service.CourseFeedbackService;

@Path("/feedback")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseFeedbackResource {

    @Inject
    CourseFeedbackService service;

    @GET
    @Path("/course/{courseId}")
    public Response getFeedbacksByCourse(@PathParam("courseId") Integer courseId) {
        return Response.ok(service.getFeedbacksByCourse(courseId)).build();
    }

    @POST
    @Transactional
    public Response submit(@QueryParam("courseId") Integer courseId,
                           @QueryParam("studentId") Integer studentId,
                           @Valid CourseFeedbackRequestDTO dto) {
        service.submitFeedback(courseId, studentId, dto);
        return Response.status(Response.Status.CREATED).build();
    }


    @DELETE
    @Path("/{feedbackId}")
    @Transactional
    public Response delete(@PathParam("feedbackId") Integer feedbackId) {
        service.deleteFeedback(feedbackId);
        return Response.noContent().build();
    }
}
