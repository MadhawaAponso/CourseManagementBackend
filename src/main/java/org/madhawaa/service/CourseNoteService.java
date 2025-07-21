package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.CourseNoteRequestDTO;
import org.madhawaa.dto.responseDTO.CourseNoteResponseDTO;
import org.madhawaa.entity.CourseNote;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.CourseNoteMapper;
import org.madhawaa.repository.CourseNoteRepository;
import org.madhawaa.repository.LectureRepository;
import org.madhawaa.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseNoteService {

    @Inject
    CourseNoteRepository noteRepository;

    @Inject
    LectureRepository lectureRepository;

    @Inject
    UserRepository userRepository;

    public List<CourseNoteResponseDTO> getNotesByLecture(Integer lectureId) {
        return noteRepository.findByLectureId(lectureId).stream()
                .map(CourseNoteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CourseNoteResponseDTO uploadNote(CourseNoteRequestDTO dto) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId());
        User uploader = userRepository.findById(dto.getUploadedBy());

        if (lecture == null || uploader == null)
            throw new NotFoundException("Invalid lecture or user");

        CourseNote note = CourseNoteMapper.toEntity(dto, lecture, uploader);
        noteRepository.persist(note);
        return CourseNoteMapper.toDTO(note);
    }

    @Transactional
    public void deleteNote(Integer noteId) {
        CourseNote note = noteRepository.findById(noteId);
        if (note == null) throw new NotFoundException("Note not found");
        noteRepository.delete(note);
    }
}
