package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "course_feedback", schema = "public", indexes = {
        @Index(name = "course_feedback_course_id_idx", columnList = "course_id"),
        @Index(name = "course_feedback_student_id_idx", columnList = "student_id"),
        @Index(name = "course_feedback_rating_idx", columnList = "rating"),
        @Index(name = "course_feedback_submitted_at_idx", columnList = "submitted_at")
})
@Data
public class CourseFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @Column(name = "feedback_text", nullable = false, length = Integer.MAX_VALUE , columnDefinition = "text")
    private String feedbackText;

    @Column(name = "rating")
    private Integer rating;

    @ColumnDefault("false")
    @Column(name = "is_anonymous")
    private Boolean isAnonymous;

    @ColumnDefault("now()")
    @Column(name = "submitted_at")
    private Instant submittedAt;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        this.submittedAt = Instant.now();
        this.isActive = true;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
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
//    public String getFeedbackText() {
//        return feedbackText;
//    }
//
//    public void setFeedbackText(String feedbackText) {
//        this.feedbackText = feedbackText;
//    }
//
//    public Integer getRating() {
//        return rating;
//    }
//
//    public void setRating(Integer rating) {
//        this.rating = rating;
//    }
//
//    public Boolean getIsAnonymous() {
//        return isAnonymous;
//    }
//
//    public void setIsAnonymous(Boolean isAnonymous) {
//        this.isAnonymous = isAnonymous;
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
//    public Boolean getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(Boolean isActive) {
//        this.isActive = isActive;
//    }

}