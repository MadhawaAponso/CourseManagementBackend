package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.LectureRequestDTO;
import org.madhawaa.dto.responseDTO.LectureResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;

public class LectureMapper {

    public static LectureResponseDTO toDTO(Lecture lecture) {
        LectureResponseDTO dto = new LectureResponseDTO();

        dto.setId(lecture.getId());
        dto.setLectureTitle(lecture.getLectureTitle());
        dto.setDescription(lecture.getDescription());
        dto.setOnlineLectureLink(lecture.getOnlineLectureLink());
        dto.setScheduledDate(lecture.getScheduledDate());
        dto.setWeekNumber(lecture.getWeekNumber());

        Course course = lecture.getCourse();
        if (course != null) {
            dto.setCourseCode(course.getCourseCode());
            dto.setCourseName(course.getCourseName());

            User instructor = course.getInstructor();
            if (instructor != null) {
                dto.setInstructorName(instructor.getFirstName() + " " + instructor.getLastName());
            }
        }

        // Other fields (notes, assignments, attendance) will be set from service logic if needed
        return dto;
    }

    public static Lecture fromDTO(LectureRequestDTO dto, Course course) {
        Lecture lecture = new Lecture();
        lecture.setLectureTitle(dto.getLectureTitle());
        lecture.setDescription(dto.getDescription());
        lecture.setOnlineLectureLink(dto.getOnlineLectureLink());
        lecture.setScheduledDate(dto.getScheduledDate());
        lecture.setWeekNumber(dto.getWeekNumber());
        lecture.setCourse(course);
        lecture.setIsActive(true);
        return lecture;
    }
}
