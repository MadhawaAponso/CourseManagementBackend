package org.madhawaa.mapper;

import org.madhawaa.dto.requestDTO.AttendanceRequestDTO;
import org.madhawaa.dto.responseDTO.AttendanceResponseDTO;
import org.madhawaa.entity.Attendance;
import org.madhawaa.entity.Lecture;
import org.madhawaa.entity.User;

public class AttendanceMapper {

    public static AttendanceResponseDTO toDTO(Attendance attendance) {
        AttendanceResponseDTO dto = new AttendanceResponseDTO();
        dto.setId(attendance.getId());
        System.out.println(attendance.getStudent());
        dto.setStudentName(attendance.getStudent().getUsername());
        dto.setLectureTitle(attendance.getLecture().getLectureTitle());
        dto.setIsPresent(attendance.getIsPresent());
        dto.setMarkedAt(attendance.getMarkedAt());
        dto.setRemarks(attendance.getRemarks());
        return dto;
    }

    public static Attendance fromDTO(AttendanceRequestDTO dto, User student, Lecture lecture) {
        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setLecture(lecture);
        attendance.setIsPresent(dto.getIsPresent() != null && dto.getIsPresent());
        attendance.setRemarks(dto.getRemarks());
        attendance.setMarkedAt(java.time.Instant.now());
        return attendance;
    }
}
