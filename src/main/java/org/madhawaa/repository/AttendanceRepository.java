package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.Attendance;

import java.util.List;

@ApplicationScoped
public class AttendanceRepository implements PanacheRepositoryBase<Attendance , Integer> {

    public List<Attendance> findByLectureId(Integer lectureId) {
        return find("lecture.id", lectureId).list();
    }

    public Attendance findByLectureAndStudent(Integer lectureId, Integer studentId) {
        return find("lecture.id = ?1 and student.id = ?2", lectureId, studentId).firstResult();
    }
}
