package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "attendance", schema = "public", indexes = {
        @Index(name = "attendance_student_id_lecture_id_idx", columnList = "student_id, lecture_id", unique = true)
})
@Data
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @ColumnDefault("false")
    @Column(name = "is_present")
    private Boolean isPresent;

    @ColumnDefault("now()")
    @Column(name = "marked_at")
    private Instant markedAt;

    @Column(name = "remarks", length = Integer.MAX_VALUE)
    private String remarks;

    @PrePersist
    public void prePersist() {
        this.markedAt = Instant.now();
        this.isPresent = this.isPresent != null && this.isPresent;
    }

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
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
//    public Lecture getLecture() {
//        return lecture;
//    }
//
//    public void setLecture(Lecture lecture) {
//        this.lecture = lecture;
//    }
//
//    public Boolean getIsPresent() {
//        return isPresent;
//    }
//
//    public void setIsPresent(Boolean isPresent) {
//        this.isPresent = isPresent;
//    }
//
//    public Instant getMarkedAt() {
//        return markedAt;
//    }
//
//    public void setMarkedAt(Instant markedAt) {
//        this.markedAt = markedAt;
//    }
//
//    public String getRemarks() {
//        return remarks;
//    }
//
//    public void setRemarks(String remarks) {
//        this.remarks = remarks;
//    }

}