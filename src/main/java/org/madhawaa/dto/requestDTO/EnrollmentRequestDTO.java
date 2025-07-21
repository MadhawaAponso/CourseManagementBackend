package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer courseId;


}
