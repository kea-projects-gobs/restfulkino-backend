package dk.kino.repository;

import dk.kino.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
    List<Reservation> findAllByScheduleId(int scheduleId);
}
