package com.example.movie_reservation_v1_backend.entity;

import com.example.movie_reservation_v1_backend.dto.show.ShowRequest;
import com.example.movie_reservation_v1_backend.entity.seat.Seat;
import com.example.movie_reservation_v1_backend.entity.seat.SeatStatus;
import com.example.movie_reservation_v1_backend.entity.seat.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "shows")
public class Show {
    @Id
    private String id;
    @DBRef
    private Movie movie;
    @DBRef
    private Theater theater;
    @Version
    private Long version;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Map<String, Seat> seats = new HashMap<>();

    public Show(ShowRequest showRequest) {
        this.startTime = showRequest.startTime();
        this.endTime = showRequest.endTime();
    }

    public void initiateSeats() {
        for (int row = 1; row <= theater.getRows(); row++) {
            for (int column = 1; column <= theater.getColumns(); column++) {
                String seatId = row + "-" + column;
                SeatType seatType = (row <= 2) ? SeatType.PREMIUM : SeatType.NORMAL;
                double price = (seatType == SeatType.NORMAL) ? 200 : 300;
                Seat seat = new Seat(seatId, row, column, seatType, price, SeatStatus.AVAILABLE);
                seats.put(seatId, seat);
            }
        }
    }
}
