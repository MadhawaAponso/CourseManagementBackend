package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.madhawaa.enums.EnrollmentStatus;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "enrollments", schema = "public", indexes = {
        @Index(name = "enrollments_student_id_course_id_idx", columnList = "student_id, course_id", unique = true)
})
@Data
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "enrollment_status")
    @ColumnDefault("'active'")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EnrollmentStatus status = EnrollmentStatus.active;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.enrollmentDate = LocalDate.now();
        if (this.status == null) {
            this.status = EnrollmentStatus.active;
        }
    }

    // --- Getters and Setters ---

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
//    public Course getCourse() {
//        return course;
//    }
//
//    public void setCourse(Course course) {
//        this.course = course;
//    }
//
//    public EnrollmentStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(EnrollmentStatus status) {
//        this.status = status;
//    }
//
//    public LocalDate getEnrollmentDate() {
//        return enrollmentDate;
//    }
//
//    public void setEnrollmentDate(LocalDate enrollmentDate) {
//        this.enrollmentDate = enrollmentDate;
//    }
//
//    public Instant getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Instant createdAt) {
//        this.createdAt = createdAt;
//    }
}


