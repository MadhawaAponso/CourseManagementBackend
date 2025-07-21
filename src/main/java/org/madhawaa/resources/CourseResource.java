package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.CourseRequestDTO;
import org.madhawaa.dto.responseDTO.CourseResponseDTO;
import org.madhawaa.service.CourseService;

import java.util.List;

@Path("/courses")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

    @Inject
    CourseService courseService;

    @GET
    public List<CourseResponseDTO> getAllCourses() {
        return courseService.listAll();
    }

    @GET
    @Path("/{courseId}")
    public Response getCourseById(@PathParam("courseId") Integer courseId) {
        return Response.ok(courseService.getById(courseId)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid CourseRequestDTO dto) {
        return Response.status(Response.Status.CREATED)
                .entity(courseService.create(dto))
                .build();
    }

    @PUT
    @Path("/{courseId}")
    @Transactional
    public Response update(@PathParam("courseId") Integer courseId, @Valid CourseRequestDTO dto) {
        return Response.ok(courseService.update(courseId, dto)).build();
    }

    @DELETE
    @Path("/{courseId}")
    @Transactional
    public Response delete(@PathParam("courseId") Integer courseId) {
        courseService.delete(courseId);
        return Response.noContent().build();
    }
    @GET
    @Path("/instructor")
    public List<CourseResponseDTO> getInstructorCourses(@QueryParam("instructorId") Integer instructorId) {
        return courseService.getCoursesByInstructor(instructorId);
    }

    @GET
    @Path("/enrolled")
    public List<CourseResponseDTO> getEnrolledCourses(@QueryParam("studentId") Integer studentId) {
        return courseService.getCoursesEnrolled(studentId);
    }

    @GET
    @Path("/available")
    public List<CourseResponseDTO> getAvailableCourses(@QueryParam("studentId") Integer studentId) {
        return courseService.getAvailableCourses(studentId);
    }
}
