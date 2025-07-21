package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.EnrollmentRequestDTO;
import org.madhawaa.dto.responseDTO.CourseResponseDTO;
import org.madhawaa.dto.responseDTO.EnrollmentResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.Enrollment;
import org.madhawaa.entity.User;
import org.madhawaa.enums.EnrollmentStatus;
import org.madhawaa.enums.Role;
import org.madhawaa.exceptions.ConflictException;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.CourseMapper;
import org.madhawaa.mapper.EnrollmentMapper;
import org.madhawaa.repository.CourseRepository;
import org.madhawaa.repository.EnrollmentRepository;
import org.madhawaa.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class EnrollmentService {

    @Inject
    EnrollmentRepository enrollmentRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    CourseRepository courseRepository;

    public List<EnrollmentResponseDTO> getEnrollmentsByStudent(Integer studentId) {
        return enrollmentRepository.findActiveEnrollmentsByStudentId(studentId).stream()
                .map(EnrollmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<CourseResponseDTO> getAvailableCourses(Integer studentId) {
        User student = userRepository.findById(studentId);
        if (student == null || student.getRole() != Role.student) {
            throw new NotFoundException("User not found or not a student");
        }

        List<Course> courses = courseRepository.findUnenrolledCourses(studentId);

        return courses.stream()
                .map(CourseMapper::toDTO)  // baseDTO will be used here
                .collect(Collectors.toList());
    }


    @Transactional
    public EnrollmentResponseDTO enroll(EnrollmentRequestDTO dto) {
        User student = userRepository.findById(dto.getStudentId());
        Course course = courseRepository.findById(dto.getCourseId());

        if (student == null || course == null)
            throw new NotFoundException("Invalid student or course");

        Enrollment existing = enrollmentRepository.findByStudentAndCourse(dto.getStudentId(), dto.getCourseId());
        if (existing != null) {
            throw new ConflictException("Already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.active);

        enrollmentRepository.persist(enrollment);
        return EnrollmentMapper.toDTO(enrollment);
    }

    @Transactional
    public void drop(Integer enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null || enrollment.getStatus() != EnrollmentStatus.active) {
            throw new NotFoundException("Enrollment not found or already dropped");
        }

        enrollment.setStatus(EnrollmentStatus.dropped);
    }
}
