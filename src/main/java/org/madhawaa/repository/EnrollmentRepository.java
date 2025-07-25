package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.smallrye.graphql.api.Scalar;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.Enrollment;
import org.madhawaa.enums.EnrollmentStatus;

import java.util.List;

@ApplicationScoped
public class EnrollmentRepository implements PanacheRepositoryBase<Enrollment , Integer> {

    public List<Enrollment> findActiveEnrollmentsByStudentId(Integer studentId) {
        return find("student.id = ?1 AND status = 'active'", studentId).list();
    }

    public Enrollment findByStudentAndCourse(Integer studentId, Integer courseId) {
        return find("student.id = ?1 AND course.id = ?2", studentId, courseId).firstResult();
    }
//    public List<Integer> findEnrolledCourseIdsExcludingDropped(Integer studentId) {
//        return getEntityManager().createQuery("""
//            SELECT e.course.id FROM Enrollment e
//             WHERE e.student.id = :studentId
//               AND e.status     <> :dropped
//        """, Integer.class)
//                .setParameter("studentId", studentId)
//                .setParameter("dropped", EnrollmentStatus.dropped)
//                .getResultList();
//    }

    public List<Integer> findCourseIdsByStudentId(Integer studentId) {
        return getEntityManager().createQuery("""
            SELECT e.course.id FROM Enrollment e
            WHERE e.student.id = :studentId
        """, Integer.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
}
