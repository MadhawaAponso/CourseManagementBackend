package org.madhawaa.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.madhawaa.dto.requestDTO.LectureRequestDTO;
import org.madhawaa.dto.responseDTO.LectureResponseDTO;
import org.madhawaa.service.LectureService;

import java.util.List;

@Path("/lectures")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LectureResource {

    @Inject
    LectureService lectureService;

    @GET
    @Path("/course/{courseId}")
    public List<LectureResponseDTO> getLecturesByCourse(@PathParam("courseId") Integer courseId) {
        return lectureService.getByCourseId(courseId);
    }

    @GET
    @Path("/{lectureId}")
    public LectureResponseDTO getLecture(@PathParam("lectureId") Integer lectureId) {
        return lectureService.getById(lectureId);
    }

    @POST
//    @Transactional
//    @RolesAllowed({"instructor"})
    @Path("/course/{courseId}")
    public LectureResponseDTO create(@PathParam("courseId") Integer courseId, @Valid LectureRequestDTO dto) {
        return lectureService.create(courseId, dto);
    }

    @PUT
//    @Transactional
//    @RolesAllowed({"instructor"})
    @Path("/{lectureId}")
    public LectureResponseDTO update(@PathParam("lectureId") Integer lectureId, @Valid LectureRequestDTO dto) {
        return lectureService.update(lectureId, dto);
    }

    @DELETE
//    @Transactional
//    @RolesAllowed({"instructor"})
    @Path("/{lectureId}")
    public Response delete(@PathParam("lectureId") Integer lectureId) {
        lectureService.delete(lectureId);
        return Response.noContent().build();
    }

    @GET
    @Path("/week/current/student")
    public List<LectureResponseDTO> getCurrentWeekForStudent(@QueryParam("studentId") Integer studentId) {
        return lectureService.getCurrentWeekForStudent(studentId);
    }

    @GET
    @Path("/week/current/instructor")
    public List<LectureResponseDTO> getCurrentWeekForInstructor(@QueryParam("instructorId") Integer instructorId) {
        return lectureService.getCurrentWeekForInstructor(instructorId);
    }
}
