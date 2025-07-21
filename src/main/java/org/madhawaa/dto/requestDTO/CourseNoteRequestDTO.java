package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseNoteRequestDTO {

    @NotNull
    private Integer lectureId;

    @NotBlank
    private String noteTitle;

    @NotBlank
    private String filePath;

    private String fileType;

    @NotNull
    private Integer uploadedBy;

    // Getters and setters...
}
