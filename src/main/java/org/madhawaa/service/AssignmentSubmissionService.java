package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.AssignmentSubmissionRequestDTO;
import org.madhawaa.dto.requestDTO.GradeSubmissionRequestDTO;
import org.madhawaa.dto.responseDTO.AssignmentSubmissionResponseDTO;
import org.madhawaa.entity.Assignment;
import org.madhawaa.entity.AssignmentSubmission;
import org.madhawaa.entity.User;
import org.madhawaa.enums.SubmissionStatus;
import org.madhawaa.exceptions.ConflictException;
import org.madhawaa.mapper.AssignmentSubmissionMapper;
import org.madhawaa.repository.AssignmentRepository;
import org.madhawaa.repository.AssignmentSubmissionRepository;
import org.madhawaa.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AssignmentSubmissionService {

    @Inject
    AssignmentRepository assignmentRepository;

    @Inject
    AssignmentSubmissionRepository submissionRepository;

    @Inject
    UserRepository userRepository;

    public AssignmentSubmissionResponseDTO getStudentSubmission(Integer assignmentId, Integer studentId) {
        AssignmentSubmission s = submissionRepository.findByAssignmentAndStudent(assignmentId, studentId);
        return s != null ? AssignmentSubmissionMapper.toDTO(s) : null;
    }

    public List<AssignmentSubmissionResponseDTO> getAllSubmissions(Integer assignmentId) {
        return submissionRepository.findByAssignmentId(assignmentId).stream()
                .map(AssignmentSubmissionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssignmentSubmissionResponseDTO submit(Integer assignmentId, String submissionText, Integer studentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId);
        User student = userRepository.findById(studentId);

        if (assignment == null || student == null) throw new IllegalArgumentException("Invalid IDs");

        AssignmentSubmission existing = submissionRepository.findByAssignmentAndStudent(
                assignmentId, studentId);

        if (existing != null) {
            throw new ConflictException(
                    String.format("Student %d has already submitted assignment %d.",
                            studentId, assignmentId));
        }


        AssignmentSubmission submission = new AssignmentSubmission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setSubmissionText(submissionText);
        submission.setSubmittedAt(Instant.now());

        boolean isLate = submission.getSubmittedAt().isAfter(assignment.getDueDate());
        submission.setIsLate(isLate);

        submissionRepository.persist(submission);
        return AssignmentSubmissionMapper.toDTO(submission);
    }

    @Transactional
    public AssignmentSubmissionResponseDTO grade(Integer submissionId, GradeSubmissionRequestDTO dto) {
        AssignmentSubmission submission = submissionRepository.findByIdOrThrow(submissionId);
        User grader = userRepository.findById(dto.getGradedBy());

        submission.setScore(dto.getScore());
        submission.setFeedback(dto.getFeedback());
        submission.setGradedBy(grader);
        submission.setGradedAt(Instant.now());
        submission.setStatus(SubmissionStatus.returned);

        return AssignmentSubmissionMapper.toDTO(submission);
    }
}
