package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.madhawaa.enums.SubmissionStatus;

import java.time.Instant;

@Entity
@Table(name = "assignment_submissions", schema = "public", indexes = {
        @Index(name = "assignment_submissions_assignment_id_student_id_idx", columnList = "assignment_id, student_id", unique = true),
        @Index(name = "assignment_submissions_assignment_id_idx", columnList = "assignment_id"),
        @Index(name = "assignment_submissions_student_id_idx", columnList = "student_id"),
        @Index(name = "assignment_submissions_submitted_at_idx", columnList = "submitted_at"),
        @Index(name = "assignment_submissions_graded_by_idx", columnList = "graded_by")
})
@Data
public class AssignmentSubmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Column(name = "submission_text", nullable = false, length = Integer.MAX_VALUE)
    private String submissionText;

    @ColumnDefault("now()")
    @Column(name = "submitted_at")
    private Instant submittedAt;

    @ColumnDefault("false")
    @Column(name = "is_late")
    private Boolean isLate;

    @Column(name = "score")
    private Integer score;

    @Column(name = "feedback", length = Integer.MAX_VALUE)
    private String feedback;

    @Column(name = "graded_at")
    private Instant gradedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "graded_by")
    private User gradedBy;

    // Add inside AssignmentSubmission.java
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private SubmissionStatus status;

    @PrePersist
    public void onCreate() {
        this.submittedAt = Instant.now();
        this.isLate = this.assignment != null && this.submittedAt.isAfter(this.assignment.getDueDate());
        this.status = SubmissionStatus.submitted;
    }

//    public Boolean getLate() {
//        return isLate;
//    }
//
//    public void setLate(Boolean late) {
//        isLate = late;
//    }
//
//    public SubmissionStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(SubmissionStatus status) {
//        this.status = status;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Assignment getAssignment() {
//        return assignment;
//    }
//
//    public void setAssignment(Assignment assignment) {
//        this.assignment = assignment;
//    }
//
//    public User getStudent() {
//        return student;
//    }
//
//    public void setStudent(User student) {
//        this.student = student;
//    }
//
//    public String getSubmissionText() {
//        return submissionText;
//    }
//
//    public void setSubmissionText(String submissionText) {
//        this.submissionText = submissionText;
//    }
//
//    public Instant getSubmittedAt() {
//        return submittedAt;
//    }
//
//    public void setSubmittedAt(Instant submittedAt) {
//        this.submittedAt = submittedAt;
//    }
//
//    public Boolean getIsLate() {
//        return isLate;
//    }
//
//    public void setIsLate(Boolean isLate) {
//        this.isLate = isLate;
//    }
//
//    public Integer getScore() {
//        return score;
//    }
//
//    public void setScore(Integer score) {
//        this.score = score;
//    }
//
//    public String getFeedback() {
//        return feedback;
//    }
//
//    public void setFeedback(String feedback) {
//        this.feedback = feedback;
//    }
//
//    public Instant getGradedAt() {
//        return gradedAt;
//    }
//
//    public void setGradedAt(Instant gradedAt) {
//        this.gradedAt = gradedAt;
//    }
//
//    public User getGradedBy() {
//        return gradedBy;
//    }
//
//    public void setGradedBy(User gradedBy) {
//        this.gradedBy = gradedBy;
//    }

/*
 TODO [Reverse Engineering] create field to map the 'status' column
 Available actions: Define target Java type | Uncomment as is | Remove column mapping
    @ColumnDefault("'submitted'")
    @Column(name = "status", columnDefinition = "submission_status")
    private Object status;
*/
}