package dk.kino.service.impl;

import dk.kino.entity.Seat;
import dk.kino.repository.SeatRepository;
import dk.kino.service.SeatService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
    @Override
    public Seat createSeat(Seat seat) {
        seat.setActive(true);
        return seatRepository.save(seat);
    }

    @Override
    public List<Seat> createSeats(Set<Seat> seats) {
        seats.forEach(seat -> seat.setActive(true));
        return seatRepository.saveAll(seats);
    }

    @Override
    public void deleteSeats(Set<Seat> seats) {
        // Soft deletion
        seats.forEach(seat -> seat.setActive(false));
        seatRepository.saveAll(seats);
    }
}
