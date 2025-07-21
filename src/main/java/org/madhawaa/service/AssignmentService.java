package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.AssignmentRequestDTO;
import org.madhawaa.dto.responseDTO.AssignmentResponseDTO;
import org.madhawaa.entity.Assignment;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.AssignmentMapper;
import org.madhawaa.repository.AssignmentRepository;
import org.madhawaa.repository.LectureRepository;
import org.madhawaa.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AssignmentService {

    @Inject
    AssignmentRepository assignmentRepository;

    @Inject
    LectureRepository lectureRepository;

    @Inject
    UserRepository userRepository;

    public List<AssignmentResponseDTO> getByLectureId(Integer lectureId) {
        return assignmentRepository.findByLectureId(lectureId).stream()
                .map(AssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<AssignmentResponseDTO> getUpcomingForStudent(Integer studentId) {
        return assignmentRepository.findUpcomingForStudent(studentId, Instant.now()).stream()
                .map(AssignmentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AssignmentResponseDTO create(AssignmentRequestDTO dto) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId());
        if (lecture == null) throw new NotFoundException("Lecture not found");

        User creator = userRepository.findById(dto.getCreatedBy());
        if (creator == null) throw new NotFoundException("User not found");

        Assignment assignment = AssignmentMapper.toEntity(dto, lecture, creator);
        assignmentRepository.persist(assignment);
        return AssignmentMapper.toDTO(assignment);
    }

    @Transactional
    public AssignmentResponseDTO update(Integer id, AssignmentRequestDTO dto) {
        Assignment assignment = assignmentRepository.findById(id);
        if (assignment == null || !assignment.getIsActive()) throw new NotFoundException("Assignment not found");

        assignment.setAssignmentTitle(dto.getAssignmentTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        assignment.setMaxScore(dto.getMaxScore() != null ? dto.getMaxScore() : 100);

        return AssignmentMapper.toDTO(assignment);
    }

    @Transactional
    public void delete(Integer id) {
        Assignment assignment = assignmentRepository.findById(id);
        if (assignment == null || !assignment.getIsActive()) throw new NotFoundException("Assignment not found");
        assignment.setIsActive(false);
    }
}
