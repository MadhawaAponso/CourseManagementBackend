package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.Course;
import org.madhawaa.entity.User;

import java.util.List;

@ApplicationScoped
public class CourseRepository implements PanacheRepositoryBase<Course , Integer> {

    public List<Course> findByInstructor(User instructor) {
        return list("instructor", instructor);
    }

    public List<Course> findByStudentEnrolled(Integer studentId) {
        return getEntityManager()
                .createQuery("SELECT c FROM Course c JOIN Enrollment e ON c.id = e.course.id WHERE e.student.id = :studentId", Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public List<Course> findAvailableForStudent(Integer studentId) {
        return getEntityManager()
                .createQuery("SELECT c FROM Course c WHERE c.id NOT IN " +
                        "(SELECT e.course.id FROM Enrollment e WHERE e.student.id = :studentId)", Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
    public List<Course> findUnenrolledCourses(Integer studentId) {
        return getEntityManager().createQuery("""
            SELECT c FROM Course c
            WHERE c.isActive = true AND c.id NOT IN (
                SELECT e.course.id FROM Enrollment e WHERE e.student.id = :studentId
            )
        """, Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
}
