package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Table(name = "attendance_passwords")
@Data
public class AttendancePassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Integer lectureId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

//    public Integer getLectureId() {
//        return lectureId;
//    }
//
//    public void setLectureId(Integer lectureId) {
//        this.lectureId = lectureId;
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
//    public String getPasswordHash() {
//        return passwordHash;
//    }
//
//    public void setPasswordHash(String passwordHash) {
//        this.passwordHash = passwordHash;
//    }
//
//    public Instant getExpiresAt() {
//        return expiresAt;
//    }
//
//    public void setExpiresAt(Instant expiresAt) {
//        this.expiresAt = expiresAt;
//    }
}
