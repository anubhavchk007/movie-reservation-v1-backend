package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.entity.Show;
import com.example.movie_reservation_v1_backend.repository.ShowRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ShowCleanupScheduler {
    private final ShowRepository showRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

    public ShowCleanupScheduler(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public void scheduleShowDeletion(Show show) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime targetTime = show.getEndTime().plusSeconds(10);
        long delay = Duration.between(now, targetTime).toMillis();

        if (delay <= 0) {
            showRepository.deleteById(show.getId());
            System.out.println("Deleted show: " + show.getId() + " at " + LocalDateTime.now());
            return;
        }
        scheduler.schedule(() -> {
           showRepository.deleteById(show.getId());
            System.out.println("Deleted show: " + show.getId() + " at " + LocalDateTime.now());
        }, delay, TimeUnit.MILLISECONDS);
    }

    @PostConstruct
    public void rescheduleAllPendingShows() {
        showRepository.findAll().forEach(this::scheduleShowDeletion);
    }
}
