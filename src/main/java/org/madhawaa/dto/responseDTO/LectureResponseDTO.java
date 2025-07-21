package org.madhawaa.dto.responseDTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;


@Data
public class LectureResponseDTO {

    private Integer id;
    private String courseCode;
    private String courseName;
    private String lectureTitle;
    private String description;
    private String onlineLectureLink;
    private Integer weekNumber;
    private LocalDateTime scheduledDate;

    // THINGS WE ARE GETTING FROM OTHER ENTITES
    private String instructorName;

    //LIST SECTION
    private List<CourseNoteResponseDTO> notes;
    private List<AssignmentResponseDTO> assignments;
//    private List<AttendanceResponseDTO> attendances;


}
