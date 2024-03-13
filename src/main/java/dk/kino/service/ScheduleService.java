package dk.kino.service;

import dk.kino.dto.ScheduleDto;
import dk.kino.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> findAll();
    List<ScheduleDto> findByDate(LocalDate date);
    List<ScheduleDto> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId,int cinemaId);
    ScheduleDto create(ScheduleDto scheduleDto);
    ScheduleDto update(int id, ScheduleDto scheduleDto);
    void delete(int id);
}
