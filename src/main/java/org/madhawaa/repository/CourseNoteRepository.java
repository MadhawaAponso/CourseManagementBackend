package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.CourseNote;

import java.util.List;

@ApplicationScoped
public class CourseNoteRepository implements PanacheRepositoryBase<CourseNote , Integer> {

    public List<CourseNote> findByLectureId(Integer lectureId) {
        return find("lecture.id = ?1", lectureId).list();
    }

    public CourseNote findByIdOrThrow(Integer noteId) {
        CourseNote note = findById(noteId);
        if (note == null) throw new IllegalArgumentException("Note not found");
        return note;
    }
}
