package org.madhawaa.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.quarkus.scheduler.Scheduled;
import jakarta.transaction.Transactional;
import org.madhawaa.repository.AttendancePasswordRepository;

import java.time.Instant;

@ApplicationScoped
public class AttendancePasswordCleanupJob {

    @Inject
    AttendancePasswordRepository repository;

    @Scheduled(every = "15m") // Run every 15 minutes
    @Transactional
        // <-- THIS IS REQUIRED
    void cleanupExpiredPasswords() {
        long deletedCount = repository.deleteExpiredPasswords();
        System.out.println("🧹 Expired attendance passwords cleaned up: " + deletedCount);
    }
}
