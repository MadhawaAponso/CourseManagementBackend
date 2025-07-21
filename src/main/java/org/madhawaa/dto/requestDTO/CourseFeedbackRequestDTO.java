package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CourseFeedbackRequestDTO {

//    @NotNull
//    private Integer courseId;

//    @NotNull
//    private Integer studentId;

    @NotBlank
    private String feedbackText;

    @Min(1)
    @Max(5)
    private Integer rating;

    private Boolean isAnonymous;

    // Getters and setters...
}
