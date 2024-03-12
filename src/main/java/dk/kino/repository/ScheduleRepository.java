package dk.kino.repository;

import dk.kino.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
//    @Query("SELECT s FROM Schedule s WHERE s.date = :date AND s.movie.id = :movieId")
//    List<Schedule> findByDateAndMovieId(@Param("date") LocalDate date, @Param("movieId") int movieId);

//    // TODO: Add when Movie Entity is created
//    List<Schedule> findByDateAndMovie(LocalDate date, Movie movie);


    // find by date and cinemaName (Hall has a FK cinema_id
//    @Query("SELECT s FROM Schedule s JOIN s.hall h WHERE h.cinema.name = :cinemaName AND s.date = :date")
//    List<Schedule> findByDateAndCinemaName(@Param("date") LocalDate date, @Param("cinemaName") String cinemaName);

    //    // TODO: Add when Movie, Cinema and Hall Entity is created
    // Find by date, cinemaName and movieName
//    @Query("SELECT s FROM Schedule s JOIN s.hall h JOIN s.movie m WHERE h.cinema.name = :cinemaName AND s.date = :date AND m.name = :movieName ORDER BY m.name")
//    List<Schedule> findByDateAndCinemaNameAndMovieName(@Param("date") LocalDate date, @Param("cinemaName") String cinemaName, @Param("movieName") String movieName);

    List<Schedule> findByDate(LocalDate date);
}
