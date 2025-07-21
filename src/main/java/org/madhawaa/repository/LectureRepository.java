package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.Lecture;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class LectureRepository implements PanacheRepositoryBase<Lecture , Integer> {

    public List<Lecture> findByCourseId(Integer courseId) {
        return find("course.id = ?1 AND isActive = true ORDER BY weekNumber", courseId).list();
    }

    public Lecture findActiveById(Integer id) {
        return find("id = ?1 AND isActive = true", id).firstResult();
    }

    public List<Lecture> findCurrentWeekForStudent(Integer studentId, LocalDate startOfWeek, LocalDate endOfWeek) {
        return getEntityManager().createQuery("""
            SELECT l FROM Lecture l
            JOIN Enrollment e ON l.course.id = e.course.id
            WHERE e.student.id = :studentId
              AND l.scheduledDate BETWEEN :startOfWeek AND :endOfWeek
              AND e.status = 'active'
              AND l.isActive = true
        """, Lecture.class)
                .setParameter("studentId", studentId)
                .setParameter("startOfWeek", startOfWeek.atStartOfDay())
                .setParameter("endOfWeek", endOfWeek.atTime(23, 59, 59))
                .getResultList();
    }

    public List<Lecture> findCurrentWeekForInstructor(Integer instructorId, LocalDate startOfWeek, LocalDate endOfWeek) {
        return getEntityManager().createQuery("""
            SELECT l FROM Lecture l
            WHERE l.course.instructor.id = :instructorId
              AND l.scheduledDate BETWEEN :startOfWeek AND :endOfWeek
              AND l.isActive = true
        """, Lecture.class)
                .setParameter("instructorId", instructorId)
                .setParameter("startOfWeek", startOfWeek.atStartOfDay())
                .setParameter("endOfWeek", endOfWeek.atTime(23, 59, 59))
                .getResultList();
    }
}
