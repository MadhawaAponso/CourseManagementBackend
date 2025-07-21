package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "lectures", schema = "public", indexes = {
        @Index(name = "lectures_course_id_idx", columnList = "course_id"),
        @Index(name = "lectures_week_number_idx", columnList = "week_number"),
        @Index(name = "lectures_scheduled_date_idx", columnList = "scheduled_date")
})
@Data
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "week_number", nullable = false)
    private Integer weekNumber;

    @Column(name = "lecture_title", nullable = false, length = 100)
    private String lectureTitle;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "online_lecture_link", length = 255)
    private String onlineLectureLink;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @PrePersist
    public void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters and Setters ...

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
//    public Integer getWeekNumber() {
//        return weekNumber;
//    }
//
//    public void setWeekNumber(Integer weekNumber) {
//        this.weekNumber = weekNumber;
//    }
//
//    public String getLectureTitle() {
//        return lectureTitle;
//    }
//
//    public void setLectureTitle(String lectureTitle) {
//        this.lectureTitle = lectureTitle;
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
//    public String getOnlineLectureLink() {
//        return onlineLectureLink;
//    }
//
//    public void setOnlineLectureLink(String onlineLectureLink) {
//        this.onlineLectureLink = onlineLectureLink;
//    }
//
//    public LocalDateTime getScheduledDate() {
//        return scheduledDate;
//    }
//
//    public void setScheduledDate(LocalDateTime scheduledDate) {
//        this.scheduledDate = scheduledDate;
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
//    public Instant getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Instant updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Boolean getActive() {
//        return isActive;
//    }
//
//    public void setActive(Boolean active) {
//        isActive = active;
//    }
}

