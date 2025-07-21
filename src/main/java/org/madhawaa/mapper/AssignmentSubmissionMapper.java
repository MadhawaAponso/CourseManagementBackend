package org.madhawaa.mapper;

import org.madhawaa.dto.responseDTO.AssignmentSubmissionResponseDTO;
import org.madhawaa.entity.AssignmentSubmission;

public class AssignmentSubmissionMapper {

    public static AssignmentSubmissionResponseDTO toDTO(AssignmentSubmission submission) {
        AssignmentSubmissionResponseDTO dto = new AssignmentSubmissionResponseDTO();

        dto.setId(submission.getId());
        dto.setSubmissionText(submission.getSubmissionText());
        dto.setSubmittedAt(submission.getSubmittedAt());
        dto.setIsLate(submission.getIsLate());
        dto.setScore(submission.getScore());
        dto.setFeedback(submission.getFeedback());
        dto.setGradedAt(submission.getGradedAt());

        dto.setStatus(submission.getStatus() != null ? submission.getStatus().name() : null);


        if (submission.getStudent() != null) {
            dto.setStudentName(submission.getStudent().getFirstName() + " " + submission.getStudent().getLastName());
        }

        if (submission.getGradedBy() != null) {
            dto.setGradedBy(submission.getGradedBy().getFirstName() + " " + submission.getGradedBy().getLastName());
        }

        if (submission.getAssignment() != null) {
            dto.setAssignmentTitle(submission.getAssignment().getAssignmentTitle());
        }

        return dto;
    }
}
