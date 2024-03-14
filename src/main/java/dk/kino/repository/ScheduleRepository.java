package dk.kino.repository;

import dk.kino.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Integer> {
    @Query("SELECT s FROM Schedule s JOIN s.hall h JOIN s.movie m WHERE h.cinema.id = :cinemaId AND s.date = :date AND m.id = :movieId ORDER BY s.startTime")
    List<Schedule> findByDateAndMovieIdAndCinemaId(@Param("date") LocalDate date, @Param("cinemaId") int cinemaId, @Param("movieId") int movieId);
    List<Schedule> findByDate(LocalDate date);
//    @Query("SELECT s FROM Schedule s JOIN s.hall h JOIN h.cinema c WHERE h.name = :hallName AND c.name = :cinemaName AND s.startTime = :startTime AND s.date = :date")
//    List<Schedule> findByHallNameAndCinemaNameAndStartTimeAndDate(
//            @Param("hallName") String hallName,
//            @Param("cinemaName") String cinemaName,
//            @Param("startTime") LocalTime startTime,
//            @Param("date") LocalDate date
//    );
//    @Query("SELECT s FROM Schedule s JOIN s.hall h WHERE h.id = :hallId AND s.startTime >= :startTime AND s.endTime <= :endTime AND DATE(s.date) = :date")
//    List<Schedule> findSchedulesBetweenStartAndEndTimeByHallIdAndDate(
//            @Param("hallId") int hallId,
//            @Param("startTime") LocalTime startTime,
//            @Param("endTime") LocalTime endTime,
//            @Param("date") LocalDate date
//    );
//    @Query("SELECT COUNT(s) FROM Schedule s " +
//            "WHERE s.date = :date " +
//            "AND s.hall.id = :hallId " +
//            "AND ((TIME(s.startTime) >= :startTime AND TIME(s.startTime) < :endTime) " +
//            "OR (TIME(s.endTime) > :startTime AND TIME(s.endTime) <= :endTime) " +
//            "OR (TIME(s.startTime) < :startTime AND TIME(s.endTime) > :endTime))")
//    int countSchedulesByDateAndHallAndTimeSpan(
//            @Param("hallId") int hallId,
//            @Param("startTime") String startTime,
//            @Param("endTime") String endTime,
//            @Param("date") LocalDate date
//    );

    @Query("SELECT COUNT(s) FROM Schedule s " +
            "WHERE s.date = :date " +
            "AND s.hall.id = :hallId " +
            "AND ((s.startTime >= :startTime AND s.startTime < :endTime) " +
            "OR (s.endTime > :startTime AND s.endTime <= :endTime) " +
            "OR (s.startTime < :startTime AND s.endTime > :endTime))")
    int countSchedulesByDateAndHallAndTimeSpan(
            @Param("hallId") int hallId,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("date") LocalDate date
    );
}
