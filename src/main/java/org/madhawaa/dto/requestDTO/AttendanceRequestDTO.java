package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendanceRequestDTO {

    @NotNull
    private Integer studentId;

    @NotNull
    private Integer lectureId;

    private Boolean isPresent;

    private String remarks;

    private String password;
}
