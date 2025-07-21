package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.CourseRequestDTO;
import org.madhawaa.dto.responseDTO.CourseFeedbackResponseDTO;
import org.madhawaa.dto.responseDTO.CourseResponseDTO;
import org.madhawaa.dto.responseDTO.LectureResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.User;

import java.util.List;

public class CourseMapper {

    public static CourseResponseDTO toDTO(Course course) {
        return baseDTO(course);
    }

    public static Course fromDTO(CourseRequestDTO dto, User instructor, User creator) {
        Course course = new Course();
        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setDescription(dto.getDescription());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setInstructor(instructor);
        course.setCreatedBy(creator);
        course.setIsActive(true);
        return course;
    }

    public static CourseResponseDTO toFullDTO(Course course, List<LectureResponseDTO> lectureDTOs , List<CourseFeedbackResponseDTO> feedbackDTOs) {
        CourseResponseDTO dto = baseDTO(course);
        dto.setLectures(lectureDTOs);
        dto.setFeedbacks(feedbackDTOs);
        return dto;
    }

    private static CourseResponseDTO baseDTO(Course course) {
        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseName(course.getCourseName());
        dto.setDescription(course.getDescription());
        dto.setStartDate(course.getStartDate());
        dto.setEndDate(course.getEndDate());
        dto.setActive(course.getIsActive());

        User instructor = course.getInstructor();
        if (instructor != null) {
            dto.setInstructorName(instructor.getFirstName() + " " + instructor.getLastName());
        }

        User createdBy = course.getCreatedBy();
        if (createdBy != null) {
            dto.setCreatedByName(createdBy.getFirstName() + " " + createdBy.getLastName());
        }

        return dto;
    }
    public static void updateFromDTO(Course course, CourseRequestDTO dto, User instructor, User creator) {
        course.setCourseCode(dto.getCourseCode());
        course.setCourseName(dto.getCourseName());
        course.setDescription(dto.getDescription());
        course.setStartDate(dto.getStartDate());
        course.setEndDate(dto.getEndDate());
        course.setInstructor(instructor);
        course.setCreatedBy(creator);
    }

}
