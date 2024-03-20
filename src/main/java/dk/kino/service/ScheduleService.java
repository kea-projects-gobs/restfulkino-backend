package dk.kino.service;

import dk.kino.dto.ScheduleDTO;
import dk.kino.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    List<ScheduleDTO> findAll();
    Optional<ScheduleDTO> findById(int id);
    List<ScheduleDTO> findByDate(LocalDate date);
    List<ScheduleDTO> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId, int cinemaId);
    ScheduleDTO create(ScheduleDTO scheduleDTO);
    ScheduleDTO update(int id, ScheduleDTO scheduleDTO);
    void delete(int id);
    ScheduleDTO toDto(Schedule schedule);
    Schedule toEntity(ScheduleDTO scheduleDTO);
}
