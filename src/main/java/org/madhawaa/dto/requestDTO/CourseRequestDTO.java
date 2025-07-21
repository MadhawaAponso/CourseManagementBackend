package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CourseRequestDTO {

    @NotBlank
    private String courseCode;

    @NotBlank
    private String courseName;

    private String description;

    @NotNull
    private Integer instructorId;

    @NotNull
    private Integer createdById;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
