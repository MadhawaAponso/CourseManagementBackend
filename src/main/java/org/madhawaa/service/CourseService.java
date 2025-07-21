package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.CourseRequestDTO;
import org.madhawaa.dto.responseDTO.CourseFeedbackResponseDTO;
import org.madhawaa.dto.responseDTO.CourseResponseDTO;
import org.madhawaa.dto.responseDTO.LectureResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.User;
import org.madhawaa.enums.Role;
import org.madhawaa.exceptions.BadRequestException;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.CourseFeedbackMapper;
import org.madhawaa.mapper.CourseMapper;
import org.madhawaa.mapper.LectureMapper;
import org.madhawaa.repository.CourseFeedbackRepository;
import org.madhawaa.repository.CourseRepository;
import org.madhawaa.repository.LectureRepository;
import org.madhawaa.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository courseRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    LectureRepository lectureRepository;

    @Inject
    CourseFeedbackRepository feedbackRepository;


    // GET LIST OF COURSES
    public List<CourseResponseDTO> listAll() {
        return courseRepository.listAll().stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO getById(Integer courseId) {
        Course course = courseRepository.findById(courseId);
        if (course == null) throw new NotFoundException("Course not found");

        // Fetch lectures for the course
        List<LectureResponseDTO> lectureDTOs = lectureRepository
                .findByCourseId(courseId)
                .stream()
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());

        // Fetch feedbacks for the course
        List<CourseFeedbackResponseDTO> feedbackDTOs = feedbackRepository
                .findByCourseId(courseId)
                .stream()
                .map(CourseFeedbackMapper::toDTO)
                .collect(Collectors.toList());

        return CourseMapper.toFullDTO(course, lectureDTOs, feedbackDTOs);
    }


    // CREATE COURSE
    @Transactional
    public CourseResponseDTO create(CourseRequestDTO dto) {
        User instructor = userRepository.findById(dto.getInstructorId());
        User creator = userRepository.findById(dto.getCreatedById());

        if (instructor == null || creator == null) {
            throw new NotFoundException("Instructor or Creator not found");
        }

        // 🔐 Check if instructor role is valid
        if (instructor.getRole() != Role.instructor) {
            throw new BadRequestException("Assigned user is not an instructor");
        }

        Course course = CourseMapper.fromDTO(dto, instructor, creator);
        courseRepository.persist(course);
        return CourseMapper.toDTO(course);
    }


    @Transactional
    public CourseResponseDTO update(Integer courseId, CourseRequestDTO dto) {
        Course course = courseRepository.findById(courseId);
        if (course == null) throw new NotFoundException("Course not found");

        User instructor = userRepository.findById(dto.getInstructorId());
        User creator = userRepository.findById(dto.getCreatedById());

        CourseMapper.updateFromDTO(course, dto, instructor, creator);

        return CourseMapper.toDTO(course);
    }

    @Transactional
    public void delete(Integer courseId) {
        Course course = courseRepository.findById(courseId);
        if (course == null) throw new NotFoundException("Course not found");
        courseRepository.delete(course);
    }

    public List<CourseResponseDTO> getCoursesByInstructor(Integer instructorId) {
        User instructor = userRepository.findById(instructorId);
        return courseRepository.findByInstructor(instructor).stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getCoursesEnrolled(Integer studentId) {
        return courseRepository.findByStudentEnrolled(studentId).stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getAvailableCourses(Integer studentId) {
        return courseRepository.findAvailableForStudent(studentId).stream()
                .map(CourseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
