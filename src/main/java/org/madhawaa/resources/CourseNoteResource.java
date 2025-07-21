package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.CourseNoteRequestDTO;
import org.madhawaa.dto.responseDTO.CourseNoteResponseDTO;
import org.madhawaa.service.CourseNoteService;

import java.util.List;

@Path("/notes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CourseNoteResource {

    @Inject
    CourseNoteService courseNoteService;

    @GET
    @Path("/lecture/{lectureId}")
    public List<CourseNoteResponseDTO> getNotes(@PathParam("lectureId") Integer lectureId) {
        return courseNoteService.getNotesByLecture(lectureId);
    }

    @POST
    public CourseNoteResponseDTO uploadNote(@Valid CourseNoteRequestDTO dto) {
        return courseNoteService.uploadNote(dto);
    }

    @DELETE
    @Path("/{noteId}")
    public Response deleteNote(@PathParam("noteId") Integer noteId) {
        courseNoteService.deleteNote(noteId);
        return Response.noContent().build();
    }
}
