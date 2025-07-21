package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.AssignmentRequestDTO;
import org.madhawaa.dto.responseDTO.AssignmentResponseDTO;
import org.madhawaa.entity.Assignment;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;

public class AssignmentMapper {

    public static Assignment toEntity(AssignmentRequestDTO dto, Lecture lecture, User creator) {
        Assignment assignment = new Assignment();
        assignment.setLecture(lecture);
        assignment.setAssignmentTitle(dto.getAssignmentTitle());
        assignment.setDescription(dto.getDescription());
        assignment.setDueDate(dto.getDueDate());
        assignment.setMaxScore(dto.getMaxScore() != null ? dto.getMaxScore() : 100);
        assignment.setCreatedBy(creator);
        assignment.setIsActive(true);
        return assignment;
    }

    public static AssignmentResponseDTO toDTO(Assignment assignment) {
        AssignmentResponseDTO dto = new AssignmentResponseDTO();
        dto.setId(assignment.getId());
        dto.setAssignmentTitle(assignment.getAssignmentTitle());
        dto.setDescription(assignment.getDescription());
        dto.setDueDate(assignment.getDueDate());
        dto.setMaxScore(assignment.getMaxScore());
        dto.setCreatedAt(assignment.getCreatedAt());

        if (assignment.getCreatedBy() != null) {
            dto.setCreatedBy(assignment.getCreatedBy().getFirstName() + " " + assignment.getCreatedBy().getLastName());
        }

        if (assignment.getLecture() != null) {
            dto.setLectureTitle(assignment.getLecture().getLectureTitle());
        }

        return dto;
    }
}
