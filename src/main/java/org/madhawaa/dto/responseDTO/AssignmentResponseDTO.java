package org.madhawaa.dto.responseDTO;

import java.time.Instant;
import lombok.Data;

@Data
public class AssignmentResponseDTO {

    private Integer id;
    private String assignmentTitle;
    private String description;
    private Instant dueDate;
    private Integer maxScore;
    private String createdBy;
    private String lectureTitle;
    private Instant createdAt;

    // Getters and setters...
}
