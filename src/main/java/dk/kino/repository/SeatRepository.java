package dk.kino.repository;

import dk.kino.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Integer> {
    List<Seat> findByHallIdAndIsActiveTrue(int hallId);
}
