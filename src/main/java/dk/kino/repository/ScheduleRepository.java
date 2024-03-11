package dk.kino.repository;

import dk.kino.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("SELECT s FROM Schedule s WHERE s.date = :date AND s.movie.id = :movieId")
    List<Schedule> findByDateAndMovieId(@Param("date") LocalDate date, @Param("movieId") int movieId);

//    // TODO: Add when Movie Entity is created
//    List<Schedule> findByDateAndMovie(LocalDate date, Movie movie);
}
