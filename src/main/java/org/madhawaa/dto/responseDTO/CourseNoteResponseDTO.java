package org.madhawaa.dto.responseDTO;
import java.time.Instant;
import lombok.Data;

@Data
public class CourseNoteResponseDTO {

    private Integer id;
    private String noteTitle;
    private String filePath;
    private String fileType;
    private Instant uploadedAt;
    private String uploadedBy;
    private String lectureTitle;

    // Getters and setters...
}
