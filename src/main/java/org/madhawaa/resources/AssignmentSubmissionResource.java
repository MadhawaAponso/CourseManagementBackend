package org.madhawaa.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.madhawaa.dto.requestDTO.AssignmentSubmissionRequestDTO;
import org.madhawaa.dto.requestDTO.GradeSubmissionRequestDTO;
import org.madhawaa.dto.responseDTO.AssignmentSubmissionResponseDTO;
import org.madhawaa.security.UserContextService;
import org.madhawaa.service.AssignmentSubmissionService;

import java.util.List;

@Path("/submissions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AssignmentSubmissionResource {

    @Inject
    AssignmentSubmissionService submissionService;

    @Inject
    UserContextService userContextService;

    @GET
    @RolesAllowed("student")
    @Path("/assignment/{assignmentId}/my")
    public AssignmentSubmissionResponseDTO getMySubmission(@PathParam("assignmentId") Integer assignmentId) {
        Integer studentId = userContextService.getUserId();
        return submissionService.getStudentSubmission(assignmentId, studentId);
    }

    @GET
    @Path("/assignment/{assignmentId}")
    public List<AssignmentSubmissionResponseDTO> getAllSubmissions(@PathParam("assignmentId") Integer assignmentId) {
        return submissionService.getAllSubmissions(assignmentId);
    }

    @POST
    @RolesAllowed("student")
    public AssignmentSubmissionResponseDTO submit(@Valid AssignmentSubmissionRequestDTO dto) {
        Integer studentId = userContextService.getUserId();
        return submissionService.submit(dto.getAssignmentId() , dto.getSubmissionText() , studentId);
    }

    @PUT
    @Path("/{submissionId}/grade")
    public AssignmentSubmissionResponseDTO grade(@PathParam("submissionId") Integer id,
                                                 @Valid GradeSubmissionRequestDTO dto) {
        return submissionService.grade(id, dto);
    }
}
