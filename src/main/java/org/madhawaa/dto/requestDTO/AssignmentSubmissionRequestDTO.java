package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AssignmentSubmissionRequestDTO {

    @NotNull
    private Integer assignmentId;

//    @NotNull
//    private Integer studentId;

    @NotBlank
    private String submissionText;
}
