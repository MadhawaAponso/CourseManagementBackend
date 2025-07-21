package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.EnrollmentRequestDTO;
import org.madhawaa.dto.responseDTO.CourseResponseDTO;
import org.madhawaa.dto.responseDTO.EnrollmentResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.service.EnrollmentService;

import java.util.List;

@Path("/enrollments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnrollmentResource {

    @Inject
    EnrollmentService enrollmentService;

    @GET
    @Path("/student/me")
    public List<EnrollmentResponseDTO> getMyEnrollments(@QueryParam("studentId") Integer studentId) {
        return enrollmentService.getEnrollmentsByStudent(studentId);
    }

    @GET
    @Path("/available")
    public List<CourseResponseDTO> getAvailableCourses(@QueryParam("studentId") Integer studentId) {
        return enrollmentService.getAvailableCourses(studentId);
    }

    @POST
    public EnrollmentResponseDTO enroll(@Valid EnrollmentRequestDTO dto) {
        return enrollmentService.enroll(dto);
    }

    @DELETE
    @Path("/{enrollmentId}")
    public Response drop(@PathParam("enrollmentId") Integer id) {
        enrollmentService.drop(id);
        return Response.noContent().build();
    }
}
