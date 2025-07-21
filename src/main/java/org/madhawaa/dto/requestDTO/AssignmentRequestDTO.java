package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.*;

import java.time.Instant;
import lombok.Data;

@Data
public class AssignmentRequestDTO {

    @NotNull
    private Integer lectureId;

    @NotBlank
    @Size(max = 100)
    private String assignmentTitle;

    @NotBlank
    private String description;

    @NotNull
    private Instant dueDate;

    private Integer maxScore;

    @NotNull
    private Integer createdBy;
}
