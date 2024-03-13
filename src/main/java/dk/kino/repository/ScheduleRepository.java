package dk.kino.repository;

import dk.kino.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("SELECT s FROM Schedule s JOIN s.hall h JOIN s.movie m WHERE h.cinema.id = :cinemaId AND s.date = :date AND m.id = :movieId ORDER BY s.startTime")
    List<Schedule> findByDateAndMovieIdAndCinemaId(@Param("date") LocalDate date, @Param("cinemaId") int cinemaId, @Param("movieId") int movieId);

    List<Schedule> findByDate(LocalDate date);
}
