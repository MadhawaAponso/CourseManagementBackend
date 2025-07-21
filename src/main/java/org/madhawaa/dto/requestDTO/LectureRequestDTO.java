package org.madhawaa.dto.requestDTO;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.madhawaa.util.Annotations.FutureOrToday;

@Data
public class LectureRequestDTO {

//    @NotNull
//    private Integer courseId;

    @NotNull
    private Integer weekNumber;

    @NotBlank
    private String lectureTitle;

    private String description;

    private String onlineLectureLink;

    @NotNull
    @FutureOrToday
    private LocalDateTime scheduledDate;


}
