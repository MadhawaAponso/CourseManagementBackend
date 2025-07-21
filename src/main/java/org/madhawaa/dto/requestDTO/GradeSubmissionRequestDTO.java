package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GradeSubmissionRequestDTO {

    @NotNull
    private Integer score;

    private String feedback;

    @NotNull
    private Integer gradedBy; // instructor ID
}
