package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.Assignment;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class AssignmentRepository implements PanacheRepositoryBase<Assignment , Integer> {

    public List<Assignment> findByLectureId(Integer lectureId) {
        return find("lecture.id = ?1 AND isActive = true ORDER BY dueDate", lectureId).list();
    }

//    public List<Assignment> findUpcomingForStudent(Integer studentId, Instant now) {
//        return getEntityManager().createQuery(
//                        """
//                        SELECT a
//                          FROM Assignment a
//                          JOIN a.lecture l
//                          JOIN l.course c
//                          JOIN c.enrollments e
//                          LEFT JOIN AssignmentSubmission s
//                            ON s.assignment = a
//                           AND s.student.id  = :studentId
//                        WHERE e.student.id  = :studentId
//                          AND a.dueDate     > :now
//                          AND a.isActive    = TRUE
//                          AND e.status      = 'active'
//                          AND (s.id IS NULL OR s.status = 'returned')
//                        ORDER BY a.dueDate
//                        """,
//                        Assignment.class
//                )
//                .setParameter("studentId", studentId)
//                .setParameter("now", now)
//                .getResultList();
//    }


    public List<Assignment> findUpcomingForStudent(Integer studentId, Instant now) {
        return getEntityManager().createQuery("""
            SELECT a FROM Assignment a
            JOIN Enrollment e ON a.lecture.course.id = e.course.id
            WHERE e.student.id = :studentId
              AND a.dueDate > :now
              AND a.isActive = true
              AND e.status = 'active'
            ORDER BY a.dueDate
        """, Assignment.class)
                .setParameter("studentId", studentId)
                .setParameter("now", now)
                .getResultList();
    }

    public Assignment findActiveById(Integer id) {
        return find("id = ?1 AND isActive = true", id).firstResult();
    }
}
