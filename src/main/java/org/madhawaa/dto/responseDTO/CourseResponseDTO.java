package org.madhawaa.dto.responseDTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
@Data
public class CourseResponseDTO {

    private Integer id;
    private String courseCode;
    private String courseName;
    private String description;
    private String instructorName;
    private String createdByName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;

    //INSIDE A COURSE THERE ARE MANY LECTURES
    private List<LectureResponseDTO> lectures;
    private List<CourseFeedbackResponseDTO> feedbacks;



}
