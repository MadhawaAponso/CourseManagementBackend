package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "assignments", schema = "public", indexes = {
        @Index(name = "assignments_lecture_id_idx", columnList = "lecture_id"),
        @Index(name = "assignments_due_date_idx", columnList = "due_date"),
        @Index(name = "assignments_created_by_idx", columnList = "created_by")
})
// NEED TO USE LOMBOK
@Data
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "assignment_title", nullable = false, length = 100)
    private String assignmentTitle;

    @Column(name = "description", nullable = false, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @ColumnDefault("100")
    @Column(name = "max_score")
    private Integer maxScore;

    @ColumnDefault("now()")
    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @PrePersist // HIBERNATE METHOD AUTOMATCIICALY EXECUTED RIGHT BEFORE ENTITY IS ENTERED TO DB
    public void prePersist() {
        this.createdAt = Instant.now();
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
//    public Lecture getLecture() {
//        return lecture;
//    }
//
//    public void setLecture(Lecture lecture) {
//        this.lecture = lecture;
//    }
//
//    public String getAssignmentTitle() {
//        return assignmentTitle;
//    }
//
//    public void setAssignmentTitle(String assignmentTitle) {
//        this.assignmentTitle = assignmentTitle;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Instant getDueDate() {
//        return dueDate;
//    }
//
//    public void setDueDate(Instant dueDate) {
//        this.dueDate = dueDate;
//    }
//
//    public Integer getMaxScore() {
//        return maxScore;
//    }
//
//    public void setMaxScore(Integer maxScore) {
//        this.maxScore = maxScore;
//    }
//
//    public Instant getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Instant createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;
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