package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.CourseNoteRequestDTO;
import org.madhawaa.dto.responseDTO.CourseNoteResponseDTO;
import org.madhawaa.entity.CourseNote;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;

public class CourseNoteMapper {

    public static CourseNote toEntity(CourseNoteRequestDTO dto, Lecture lecture, User uploader) {
        CourseNote note = new CourseNote();
        note.setLecture(lecture);
        note.setNoteTitle(dto.getNoteTitle());
        note.setFilePath(dto.getFilePath());
        note.setFileType(dto.getFileType());
        note.setUploadedAt(java.time.Instant.now());
        note.setUploadedBy(uploader);
        return note;
    }

    public static CourseNoteResponseDTO toDTO(CourseNote note) {
        CourseNoteResponseDTO dto = new CourseNoteResponseDTO();
        dto.setId(note.getId());
        dto.setNoteTitle(note.getNoteTitle());
        dto.setFilePath(note.getFilePath());
        dto.setFileType(note.getFileType());
        dto.setUploadedAt(note.getUploadedAt());

        if (note.getUploadedBy() != null) {
            dto.setUploadedBy(note.getUploadedBy().getFirstName() + " " + note.getUploadedBy().getLastName());
        }

        if (note.getLecture() != null) {
            dto.setLectureTitle(note.getLecture().getLectureTitle());
        }

        return dto;
    }
}
