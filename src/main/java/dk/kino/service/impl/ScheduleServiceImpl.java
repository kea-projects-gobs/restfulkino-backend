package dk.kino.service.impl;

import dk.kino.dto.MovieDTO;
import dk.kino.dto.ScheduleDto;
import dk.kino.entity.Hall;
import dk.kino.entity.Movie;
import dk.kino.entity.Schedule;
import dk.kino.repository.ScheduleRepository;
import dk.kino.service.MovieService;
import dk.kino.service.ScheduleService;
import dk.kino.service.hall.HallService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MovieService movieService;
    private final HallService hallService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository,MovieService movieService,HallService hallService) {
        this.scheduleRepository = scheduleRepository;
        this.movieService = movieService;
        this.hallService = hallService;
    }

    @Override
    public List<ScheduleDto> findAll() {
        return scheduleRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> findByDate(LocalDate date) {
            return scheduleRepository.findByDate(date).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId, int cinemaId) {
        return scheduleRepository.findByDateAndMovieIdAndCinemaId(date,cinemaId,movieId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ScheduleDto create(ScheduleDto scheduleDto) {
        Schedule schedule = toEntity(scheduleDto);
        validateScheduleUniqueness(schedule);
        schedule.setLongMovie(isLongMovie(schedule));
        schedule.setEndTime(getEndTime(schedule));
        return toDto(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleDto update(int id,ScheduleDto scheduleDto) {
        // Find schedule
        Schedule originalSchedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));

        // Map to entity
        Schedule schedule = toEntity(scheduleDto);
        validateScheduleUniqueness(schedule);

        // Update original schedule
        originalSchedule.setDate(schedule.getDate());
        originalSchedule.setStartTime(schedule.getStartTime());
        originalSchedule.setEndTime(getEndTime(schedule));
        originalSchedule.set3d(schedule.is3d());
        originalSchedule.setLongMovie(isLongMovie(schedule));
        originalSchedule.setHall(schedule.getHall());
        originalSchedule.setMovie(schedule.getMovie());

        // Persist
        return toDto(scheduleRepository.save(originalSchedule));
    }

    private LocalTime getEndTime(Schedule schedule) {
        int COMMERCIAL_DURATION = 15;
        return schedule.getStartTime().plusMinutes(schedule.getMovie().getDuration()+ COMMERCIAL_DURATION);
    }

    private void validateScheduleUniqueness(Schedule schedule) {
        int countConflicts = scheduleRepository.countSchedulesByDateAndHallAndTimeSpan(
                schedule.getHall().getId(),schedule.getStartTime(),schedule.getEndTime(),schedule.getDate(),
                schedule.getId() != null ? schedule.getId() : -1 // Passes the current schedule's ID (so that we can PUT this) and uses -1 if null
        );
        if (countConflicts>0) {
            throw new RuntimeException("Schedule already exists.");
        }
    }

    private boolean isLongMovie(Schedule schedule) {
        MovieDTO movieDTO = movieService.findById(schedule.getMovie().getId()).orElseThrow(() -> new RuntimeException(("Unable to find movie")));
        return movieDTO.getDuration()>170;
    }

    @Override
    public void delete(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));
        scheduleRepository.delete(schedule);
    }

    @Override
    public ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .endTime(schedule.getEndTime())
                .is3d(schedule.is3d())
                .isLongMovie(schedule.isLongMovie())
                .hallName(schedule.getHall().getName())
                .movieTitle(schedule.getMovie().getTitle())
                .cinemaName(schedule.getHall().getCinema().getName())
                .build();
    }

    @Override
    public Schedule toEntity(ScheduleDto scheduleDto) {
        Hall hall = hallService.convertToEntity(hallService.findByNameAndCinemaName(scheduleDto.getHallName(),scheduleDto.getCinemaName()));
        Movie movie = movieService.toEntity(movieService.findByTitle(scheduleDto.getMovieTitle()).orElse(null));
        Schedule schedule = Schedule.builder()
                .id(scheduleDto.getId())
                .date(scheduleDto.getDate())
                .startTime(scheduleDto.getStartTime())
//                .endTime(scheduleDto.getStartTime().plusMinutes(movie.getDuration()))
                .is3d(scheduleDto.is3d())
                .isLongMovie(scheduleDto.isLongMovie())
                .movie(movie)
                .hall(hall)
                .build();
        schedule.setEndTime(getEndTime(schedule));
        return schedule;
    }
}
