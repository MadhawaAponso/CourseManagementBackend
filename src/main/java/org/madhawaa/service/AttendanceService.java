package org.madhawaa.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.madhawaa.dto.requestDTO.AttendanceRequestDTO;
import org.madhawaa.dto.responseDTO.AttendanceResponseDTO;
import org.madhawaa.entity.*;
import org.madhawaa.exceptions.BadRequestException;
import org.madhawaa.exceptions.ForbiddenException;
import org.madhawaa.exceptions.NotFoundException;
import org.madhawaa.mapper.AttendanceMapper;
import org.madhawaa.repository.*;
import org.madhawaa.util.PasswordUtil;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AttendanceService {

    @Inject
    AttendanceRepository attendanceRepository;

    @Inject
    LectureRepository lectureRepository;

    @Inject
    UserRepository userRepository;

    @Inject
    AttendancePasswordRepository attendancePasswordRepository;

    @Transactional
    public void markAttendance(AttendanceRequestDTO dto) {
        Lecture lecture = lectureRepository.findById(dto.getLectureId());
        if (lecture == null) throw new NotFoundException("Lecture not found");

        User student = userRepository.findById(dto.getStudentId());
        if (student == null) throw new NotFoundException("Student not found");

        if (attendanceRepository.findByLectureAndStudent(dto.getLectureId(), dto.getStudentId()) != null) {
            throw new BadRequestException("Already marked");
        }

        AttendancePassword pwRecord = attendancePasswordRepository.findValidPassword(dto.getLectureId());
        if (pwRecord == null || !PasswordUtil.verify(dto.getPassword(), pwRecord.getPasswordHash())) {
            throw new ForbiddenException("Invalid or expired password");
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student); // ✅ must not be null
        attendance.setLecture(lecture);
        attendance.setIsPresent(true);
        attendanceRepository.persist(attendance);
    }


    public List<AttendanceResponseDTO> getLectureAttendance(Integer lectureId) {
        return attendanceRepository.findByLectureId(lectureId)
                .stream().map(AttendanceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRemark(Integer attendanceId, String remark) {
        Attendance attendance = attendanceRepository.findById(attendanceId);
        if (attendance == null) throw new NotFoundException("Attendance not found");
        attendance.setRemarks(remark);
    }

    @Transactional
    public void setAttendancePassword(Integer lectureId, String plainPassword) {
        Lecture lecture = lectureRepository.findById(lectureId);
        if (lecture == null) throw new NotFoundException("Lecture not found");

        String hashed = PasswordUtil.hash(plainPassword);

        AttendancePassword existing = attendancePasswordRepository.findByLectureId(lectureId);
        if (existing == null) {
            existing = new AttendancePassword();
            existing.setLecture(lecture);
            existing.setLectureId(lectureId);
        }

        existing.setPasswordHash(hashed);
        existing.setExpiresAt(java.time.Instant.now().plusSeconds(30 * 60)); // 30 min
        attendancePasswordRepository.persist(existing);
    }
}
