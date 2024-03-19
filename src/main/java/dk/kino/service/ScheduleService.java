package dk.kino.service;

import dk.kino.dto.ScheduleDTO;
import dk.kino.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<ScheduleDTO> findAll();
    List<ScheduleDTO> findByDate(LocalDate date);
    List<ScheduleDTO> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId, int cinemaId);
    ScheduleDTO create(ScheduleDTO scheduleDto);
    ScheduleDTO update(int id, ScheduleDTO scheduleDto);
    void delete(int id);
    ScheduleDTO toDto(Schedule schedule);
    Schedule toEntity(ScheduleDTO scheduleDto);
}
