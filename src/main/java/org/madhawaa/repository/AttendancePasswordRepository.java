package org.madhawaa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.madhawaa.entity.AttendancePassword;

import java.time.Instant;

@ApplicationScoped
public class AttendancePasswordRepository implements PanacheRepositoryBase<AttendancePassword , Integer> {

    public AttendancePassword findValidPassword(Integer lectureId) {
        return find("lectureId = ?1 and expiresAt > ?2", lectureId, Instant.now()).firstResult();
    }

    public AttendancePassword findByLectureId(Integer lectureId) {
        return find("lectureId", lectureId).firstResult();
    }

    public long deleteExpiredPasswords() {
        return delete("expiresAt < ?1", Instant.now());
    }

}
