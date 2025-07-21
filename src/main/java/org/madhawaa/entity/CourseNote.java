package org.madhawaa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "course_notes", schema = "public", indexes = {
        @Index(name = "course_notes_lecture_id_idx", columnList = "lecture_id"),
        @Index(name = "course_notes_uploaded_by_idx", columnList = "uploaded_by")
})
@Data
public class CourseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "note_title", nullable = false, length = 100)
    private String noteTitle;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_type", length = 50)
    private String fileType;

    @ColumnDefault("now()")
    @Column(name = "uploaded_at")
    private Instant uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by")
    private User uploadedBy;

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
//    public String getNoteTitle() {
//        return noteTitle;
//    }
//
//    public void setNoteTitle(String noteTitle) {
//        this.noteTitle = noteTitle;
//    }
//
//    public String getFilePath() {
//        return filePath;
//    }
//
//    public void setFilePath(String filePath) {
//        this.filePath = filePath;
//    }
//
//    public String getFileType() {
//        return fileType;
//    }
//
//    public void setFileType(String fileType) {
//        this.fileType = fileType;
//    }
//
//    public Instant getUploadedAt() {
//        return uploadedAt;
//    }
//
//    public void setUploadedAt(Instant uploadedAt) {
//        this.uploadedAt = uploadedAt;
//    }
//
//    public User getUploadedBy() {
//        return uploadedBy;
//    }
//
//    public void setUploadedBy(User uploadedBy) {
//        this.uploadedBy = uploadedBy;
//    }

}