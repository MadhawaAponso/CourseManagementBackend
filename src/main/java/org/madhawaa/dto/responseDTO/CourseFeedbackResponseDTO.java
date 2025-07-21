package org.madhawaa.dto.responseDTO;

import java.time.Instant;
import lombok.Data;

@Data
public class CourseFeedbackResponseDTO {

    private Integer id;
    private Integer courseId;
    private String studentName; // null if anonymous
    private String feedbackText;
    private Integer rating;
    private Instant submittedAt;
    private boolean isAnonymous;

    // Getters and setters...
}
