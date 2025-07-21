package org.madhawaa.dto.responseDTO;

import lombok.Data;

import java.time.Instant;

@Data
public class AssignmentSubmissionResponseDTO {
    private Integer id;
    private String studentName;
    private String submissionText;
    private Instant submittedAt;
    private Boolean isLate;
    private Integer score;
    private String feedback;
    private Instant gradedAt;
    private String gradedBy;
    private String assignmentTitle;
    private String status;

}
