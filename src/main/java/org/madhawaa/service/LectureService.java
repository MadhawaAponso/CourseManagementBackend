package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.LectureRequestDTO;
import org.madhawaa.dto.responseDTO.LectureResponseDTO;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.Lecture;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.AssignmentMapper;
import org.madhawaa.mapper.CourseNoteMapper;
import org.madhawaa.mapper.LectureMapper;
import org.madhawaa.repository.AssignmentRepository;
import org.madhawaa.repository.CourseNoteRepository;
import org.madhawaa.repository.CourseRepository;
import org.madhawaa.repository.LectureRepository;

import java.time.*;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class LectureService {

    @Inject
    LectureRepository lectureRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    CourseNoteRepository courseNoteRepository;

    @Inject
    AssignmentRepository assignmentRepository;

    public List<LectureResponseDTO> getByCourseId(Integer courseId) {
        return lectureRepository.findByCourseId(courseId).stream()
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LectureResponseDTO getById(Integer lectureId) {
        Lecture lecture = lectureRepository.findActiveById(lectureId);
        if (lecture == null) throw new NotFoundException("Lecture not found");

        LectureResponseDTO dto = LectureMapper.toDTO(lecture);

        dto.setNotes(
                courseNoteRepository.findByLectureId(lectureId).stream()
                        .map(CourseNoteMapper::toDTO)
                        .collect(Collectors.toList())
        );

        dto.setAssignments(
                assignmentRepository.findByLectureId(lectureId).stream()
                        .map(AssignmentMapper::toDTO)
                        .collect(Collectors.toList())
        );

        return dto;
    }


    @Transactional
    public LectureResponseDTO create(Integer courseId, LectureRequestDTO dto) {
        Course course = courseRepository.findById(courseId);
        if (course == null) throw new NotFoundException("Course not found");

        Lecture lecture = LectureMapper.fromDTO(dto, course);
        lectureRepository.persist(lecture);
        return LectureMapper.toDTO(lecture);
    }

    @Transactional
    public LectureResponseDTO update(Integer lectureId, LectureRequestDTO dto) {
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null || !lecture.getIsActive()) throw new NotFoundException("Lecture not found");

        lecture.setLectureTitle(dto.getLectureTitle());
        lecture.setDescription(dto.getDescription());
        lecture.setOnlineLectureLink(dto.getOnlineLectureLink());
        lecture.setScheduledDate(dto.getScheduledDate());
        lecture.setWeekNumber(dto.getWeekNumber());

        return LectureMapper.toDTO(lecture);
    }

    @Transactional
    public void delete(Integer lectureId) {
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null || !lecture.getIsActive()) throw new NotFoundException("Lecture not found");
        lecture.setIsActive(false);
    }

//    public List<LectureResponseDTO> getCurrentWeekForStudent(Integer studentId) {
//        LocalDate today = LocalDate.now();
//        LocalDate start = today.with(java.time.DayOfWeek.MONDAY);
//        LocalDate end = today.with(java.time.DayOfWeek.SUNDAY);
//
//        return lectureRepository.findCurrentWeekForStudent(studentId, start, end).stream()
//                .map(LectureMapper::toDTO)
//                .collect(Collectors.toList());
//    }
public List<LectureResponseDTO> getCurrentWeekForStudent(Integer studentId) {
    LocalDate today       = LocalDate.now();
    LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
    LocalDate endOfWeek   = today.with(DayOfWeek.SUNDAY);

    // define the window
    LocalDateTime now          = LocalDateTime.now();
    LocalDateTime weekStart    = startOfWeek.atStartOfDay();
    LocalDateTime weekEnd      = endOfWeek.atTime(LocalTime.MAX);

    return lectureRepository
            .findCurrentWeekForStudentBetween(studentId, weekStart, weekEnd)
            .stream()
            // only keep those still in the future
            .filter(l -> l.getScheduledDate().isAfter(now))
            .map(LectureMapper::toDTO)
            .collect(Collectors.toList());
}

    public List<LectureResponseDTO> getCurrentWeekForInstructor(Integer instructorId) {
        LocalDate today = LocalDate.now();
        LocalDate start = today.with(java.time.DayOfWeek.MONDAY);
        LocalDate end = today.with(java.time.DayOfWeek.SUNDAY);

        return lectureRepository.findCurrentWeekForInstructor(instructorId, start, end).stream()
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }
}
