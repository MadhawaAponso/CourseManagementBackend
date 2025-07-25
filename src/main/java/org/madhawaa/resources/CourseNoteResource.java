package org.madhawaa.resources;

import jakarta.inject.Inject;
import jakarta.validation.Valid;

import jakarta.ws.rs.Path;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.madhawaa.dto.requestDTO.CourseNoteRequestDTO;
import org.madhawaa.dto.requestDTO.FileUploadRequestDTO;
import org.madhawaa.dto.responseDTO.CourseNoteResponseDTO;
import org.madhawaa.service.CourseNoteService;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Files;

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

    @POST
    @Path("/upload")
    public Response uploadNoteFile(@Valid FileUploadRequestDTO req) {
        try {
            byte[] data = Base64.getDecoder().decode(req.getBase64());
            java.nio.file.Path dir = Paths.get("uploads/notes");
            Files.createDirectories(dir);

            java.nio.file.Path file = dir.resolve(req.getFileName());
            Files.write(file, data);

            return Response
                    .ok(Map.of("path", "/uploads/notes/" + req.getFileName()))
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{noteId}")
    public Response deleteNote(@PathParam("noteId") Integer noteId) {
        courseNoteService.deleteNote(noteId);
        return Response.noContent().build();
    }
}
