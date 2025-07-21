package org.madhawaa.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.madhawaa.entity.*;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.exceptions.ForbiddenException;
import org.madhawaa.repository.*;

@ApplicationScoped
public class ValidationUtil {

    @Inject
    LectureRepository lectureRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    UserRepository userRepository;

    public Course validateCourseExists(Integer courseId) {
        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new NotFoundException("Course not found");
        }
        return course;
    }

    public Lecture validateLectureInCourse(Integer lectureId, Integer courseId) {
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null || lecture.getCourse() == null || !lecture.getCourse().getId().equals(courseId)) {
            throw new NotFoundException("Lecture not found in specified course");
        }
        return lecture;
    }

    public void validateInstructorOwnsCourse(Integer instructorId, Integer courseId) {
        Course course = validateCourseExists(courseId);
        if (course.getInstructor() == null || !course.getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("Instructor does not own this course");
        }
    }

    public User validateUserExists(Integer userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new NotFoundException("User not found");
        }
        return user;
    }
}
