package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.CourseFeedback;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CourseFeedbackRepository implements PanacheRepositoryBase<CourseFeedback , Integer> {

    public List<CourseFeedback> findByCourseId(Integer courseId) {
        return find("course.id = ?1 and isActive = true order by submittedAt desc", courseId).list();
    }

    public CourseFeedback findActiveById(Integer id) {
        return find("id = ?1 and isActive = true", id).firstResult();
    }
    public Optional<CourseFeedback> findActiveByCourseAndStudent(Integer courseId, Integer studentId) {
        return find("course.id = ?1 and student.id = ?2 and isActive = true",
                courseId, studentId)
                .firstResultOptional();
    }
}
