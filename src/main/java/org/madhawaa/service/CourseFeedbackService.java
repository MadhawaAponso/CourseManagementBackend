package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.CourseFeedbackRequestDTO;
import org.madhawaa.dto.responseDTO.CourseFeedbackResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.CourseFeedback;
import org.madhawaa.entity.User;
import org.madhawaa.exceptions.ConflictException;    // ⬆️ Added
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.CourseFeedbackMapper;
import org.madhawaa.repository.CourseFeedbackRepository;
import org.madhawaa.repository.CourseRepository;
import org.madhawaa.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseFeedbackService {

    @Inject CourseFeedbackRepository feedbackRepo;
    @Inject CourseRepository courseRepo;
    @Inject UserRepository userRepo;

    public List<CourseFeedbackResponseDTO> getFeedbacksByCourse(Integer courseId) {
        return feedbackRepo.findByCourseId(courseId).stream()
                .map(CourseFeedbackMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ⬆️ Added: retrieve the student’s own feedback
    public CourseFeedbackResponseDTO getMyFeedback(Integer courseId, Integer studentId) {
        return feedbackRepo.findActiveByCourseAndStudent(courseId, studentId)
                .map(CourseFeedbackMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("No feedback found for this student"));
    }

    @Transactional
    public void submitFeedback(Integer courseId, Integer studentId, CourseFeedbackRequestDTO dto) {
        // ⬇️ Modified: prevent duplicate
        if (feedbackRepo.findActiveByCourseAndStudent(courseId, studentId).isPresent()) {
            throw new ConflictException("Feedback already submitted by this student");
        }

        Course course = courseRepo.findByIdOptional(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found"));
        User student = userRepo.findByIdOptional(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        CourseFeedback feedback = CourseFeedbackMapper.fromDTO(dto, course, student);
        feedbackRepo.persist(feedback);
    }

    @Transactional
    public void deleteFeedback(Integer feedbackId) {
        CourseFeedback feedback = feedbackRepo.findActiveById(feedbackId);
        if (feedback == null) throw new NotFoundException("Feedback not found");
        feedback.setIsActive(false);
    }
}
