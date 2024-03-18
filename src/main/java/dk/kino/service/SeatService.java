package dk.kino.service;

import dk.kino.entity.Seat;

import java.util.List;
import java.util.Set;

public interface SeatService {
    Seat createSeat(Seat seat);
    List<Seat> createSeats(Set<Seat> seats);
    void deleteSeats(Set<Seat> seats);
}
