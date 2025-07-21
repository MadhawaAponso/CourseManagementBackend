package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.CourseFeedbackRequestDTO;
import org.madhawaa.dto.responseDTO.CourseFeedbackResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.CourseFeedback;
import org.madhawaa.entity.User;

public class CourseFeedbackMapper {

    public static CourseFeedback fromDTO(CourseFeedbackRequestDTO dto, Course course, User student) {
        CourseFeedback feedback = new CourseFeedback();
        feedback.setCourse(course);
        feedback.setStudent(student);
        feedback.setFeedbackText(dto.getFeedbackText());
        feedback.setRating(dto.getRating());
        feedback.setIsAnonymous(dto.getIsAnonymous() != null && dto.getIsAnonymous());
        return feedback;
    }

    public static CourseFeedbackResponseDTO toDTO(CourseFeedback feedback) {
        CourseFeedbackResponseDTO dto = new CourseFeedbackResponseDTO();
        dto.setId(feedback.getId());
        dto.setCourseId(feedback.getCourse().getId());
        dto.setFeedbackText(feedback.getFeedbackText());
        dto.setRating(feedback.getRating());
        dto.setSubmittedAt(feedback.getSubmittedAt());
        dto.setAnonymous(feedback.getIsAnonymous());

        if (!dto.isAnonymous()) {
            User student = feedback.getStudent();
            dto.setStudentName(student.getFirstName() + " " + student.getLastName());
        }

        return dto;
    }
}
