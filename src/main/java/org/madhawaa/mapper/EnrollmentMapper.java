package org.madhawaa.mapper;

import org.madhawaa.dto.responseDTO.EnrollmentResponseDTO;
import org.madhawaa.entity.Enrollment;

public class EnrollmentMapper {

    public static EnrollmentResponseDTO toDTO(Enrollment enrollment) {
        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();

        dto.setId(enrollment.getId());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());
        dto.setStatus(enrollment.getStatus());

        if (enrollment.getStudent() != null) {
            dto.setStudentName(enrollment.getStudent().getFirstName() + " " + enrollment.getStudent().getLastName());
        }

        if (enrollment.getCourse() != null) {
            dto.setCourseName(enrollment.getCourse().getCourseName());
            dto.setCourseCode(enrollment.getCourse().getCourseCode());
        }

        dto.setDropAllowed(enrollment.getStatus().name().equalsIgnoreCase("active"));

        return dto;
    }
}
