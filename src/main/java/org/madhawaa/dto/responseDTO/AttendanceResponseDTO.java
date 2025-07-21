package org.madhawaa.dto.responseDTO;

import java.time.Instant;
import lombok.Data;

@Data
public class AttendanceResponseDTO {

    private Integer id;
    private String studentName;
    private String lectureTitle;
    private Boolean isPresent;
    private Instant markedAt;
    private String remarks;
}
