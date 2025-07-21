package org.madhawaa.dto.responseDTO;

import org.madhawaa.enums.EnrollmentStatus;

import java.time.LocalDate;
import lombok.Data;


@Data
public class EnrollmentResponseDTO {

    private Integer id;
    private String studentName;
    private String courseName;
    private String courseCode;
    private LocalDate enrollmentDate;
    private EnrollmentStatus status;
    private Boolean dropAllowed;

}
