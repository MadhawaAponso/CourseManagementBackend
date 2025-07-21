package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.AttendanceRequestDTO;
import org.madhawaa.dto.requestDTO.AttendancePasswordRequestDTO;
import org.madhawaa.service.AttendanceService;

@Path("/attendance")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AttendanceResource {

    @Inject
    AttendanceService service;

    @POST
    @Path("/mark")
    @Transactional
    public Response markAttendance(@Valid AttendanceRequestDTO dto) {
        service.markAttendance(dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/lecture/{lectureId}")
    public Response getLectureAttendance(@PathParam("lectureId") Integer lectureId) {
        return Response.ok(service.getLectureAttendance(lectureId)).build();
    }

    @PUT
    @Path("/{attendanceId}/remark")
    @Transactional
    public Response addRemark(@PathParam("attendanceId") Integer attendanceId,
                              @QueryParam("remark") String remark) {
        service.addRemark(attendanceId, remark);
        return Response.ok().build();
    }

    @PUT
    @Path("/lecture/{lectureId}/password")
    @Transactional
    public Response setPassword(@PathParam("lectureId") Integer lectureId,
                                @Valid AttendancePasswordRequestDTO dto) {
        service.setAttendancePassword(lectureId, dto.getPassword());
        return Response.ok().build();
    }
}
