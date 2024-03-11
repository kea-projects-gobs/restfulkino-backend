package dk.kino.service;

import dk.kino.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findAll();
    List<Schedule> findByDate(LocalDate date);
    List<Schedule> findByDateAndMovieId(LocalDate date, int movieId);
    Schedule create(Schedule schedule);
    Schedule update(int id, Schedule schedule);
    void delete(int id);
}
