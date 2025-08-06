package com.example.movie_reservation_v1_backend.service;

import com.example.movie_reservation_v1_backend.dto.show.ShowRequest;
import com.example.movie_reservation_v1_backend.dto.show.ShowResponse;
import com.example.movie_reservation_v1_backend.entity.Movie;
import com.example.movie_reservation_v1_backend.entity.Show;
import com.example.movie_reservation_v1_backend.entity.Theater;
import com.example.movie_reservation_v1_backend.entity.seat.Seat;
import com.example.movie_reservation_v1_backend.entity.seat.SeatStatus;
import com.example.movie_reservation_v1_backend.repository.MovieRepository;
import com.example.movie_reservation_v1_backend.repository.ShowRepository;
import com.example.movie_reservation_v1_backend.repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final ShowCleanupScheduler showCleanupScheduler;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, TheaterRepository theaterRepository, ShowCleanupScheduler showCleanupScheduler) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.showCleanupScheduler = showCleanupScheduler;
    }

    public ShowResponse createShow(ShowRequest showRequest) {
        Movie movie = movieRepository.findById(showRequest.movieId())
                .orElseThrow(() -> new RuntimeException("Movie not found."));
        Theater theater = theaterRepository.findById(showRequest.theaterId())
                .orElseThrow(() -> new RuntimeException("Theater doesn't exist."));
        Show show = new Show(showRequest);
        show.setMovie(movie);
        show.setTheater(theater);
        show.initiateSeats();
        showRepository.save(show);
        showCleanupScheduler.scheduleShowDeletion(show);
        return new ShowResponse(show);
    }

    public List<ShowResponse> getAllShows() {
        List<Show> allShows = showRepository.findAll();
        List<ShowResponse> allShowsResponse = new ArrayList<>();
        for (Show show : allShows) {
            allShowsResponse.add(new ShowResponse(show));
        }
        return allShowsResponse;
    }

    public ShowResponse getShowById(String id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found."));
        return new ShowResponse(show);
    }

    public List<ShowResponse> getAllShowsByMovie(String movieId) {
        List<Show> allShows = showRepository.findAllShowsByMovieId(movieId);
        List<ShowResponse> allShowsResponse = new ArrayList<>();
        for (Show show : allShows) {
            allShowsResponse.add(new ShowResponse(show));
        }
        return allShowsResponse;
    }

    public List<ShowResponse> getAllShowsByTheater(String theaterId) {
        List<Show> allShows = showRepository.findAllShowsByTheaterId(theaterId);
        List<ShowResponse> allShowsResponse = new ArrayList<>();
        for (Show show : allShows) {
            allShowsResponse.add(new ShowResponse(show));
        }
        return allShowsResponse;
    }

    public ShowResponse updateShowById(String id, ShowRequest showRequest) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found."));
        show.setStartTime(showRequest.startTime());
        show.setEndTime(showRequest.endTime());
        showRepository.save(show);
        return new ShowResponse(show);
    }

    public ShowResponse deleteShowById(String id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found."));
        showRepository.delete(show);
        return new ShowResponse(show);
    }

    public List<String> getAvailableSeats(String id) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found."));
        return show.getSeats().values().stream()
                .filter(seat -> seat.getStatus() == SeatStatus.AVAILABLE)
                .map(Seat::getSeatId)
                .sorted()
                .toList();
    }

    public List<ShowResponse> getAllShowsByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return showRepository.findByStartTimeBetween(startOfDay, endOfDay);
    }
}
