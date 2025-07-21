package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.AssignmentSubmission;

import java.util.List;

@ApplicationScoped
public class AssignmentSubmissionRepository implements PanacheRepositoryBase<AssignmentSubmission , Integer> {

    public AssignmentSubmission findByAssignmentAndStudent(Integer assignmentId, Integer studentId) {
        return find("assignment.id = ?1 AND student.id = ?2", assignmentId, studentId).firstResult();
    }

    public List<AssignmentSubmission> findByAssignmentId(Integer assignmentId) {
        return find("assignment.id = ?1", assignmentId).list();
    }

    public AssignmentSubmission findByIdOrThrow(Integer id) {
        AssignmentSubmission s = findById(id);
        if (s == null) throw new IllegalArgumentException("Submission not found");
        return s;
    }
}
