package dk.kino.service.impl;

import dk.kino.dto.MovieDTO;
import dk.kino.dto.ScheduleDTO;
import dk.kino.entity.Hall;
import dk.kino.entity.Movie;
import dk.kino.entity.Schedule;
import dk.kino.exception.BadRequestException;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.ScheduleRepository;
import dk.kino.service.MovieService;
import dk.kino.service.ScheduleService;
import dk.kino.service.HallService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
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
    public List<ScheduleDTO> findAll() {
        return scheduleRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ScheduleDTO> findById(int id) {
        return scheduleRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<ScheduleDTO> findByDate(LocalDate date) {
            return scheduleRepository.findByDate(date).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId, int cinemaId) {
        return scheduleRepository.findByDateAndMovieIdAndCinemaId(date,cinemaId,movieId).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public ScheduleDTO create(ScheduleDTO scheduleDTO) {
        Schedule schedule = toEntity(scheduleDTO);
        validateScheduleUniqueness(schedule);
        schedule.setLongMovie(isLongMovie(schedule));
        schedule.setEndTime(getEndTime(schedule));
        return toDto(scheduleRepository.save(schedule));
    }

    @Override
    public ScheduleDTO update(int id, ScheduleDTO scheduleDTO) {
        // Find schedule
        Schedule originalSchedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(("Unable to find schedule with id=" + id)));

        // Map to entity
        Schedule schedule = toEntity(scheduleDTO);
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
            throw new BadRequestException("Schedule already exists.");
        }
    }

    private boolean isLongMovie(Schedule schedule) {
        MovieDTO movieDTO = movieService.findById(schedule.getMovie().getId()).orElseThrow(() -> new NotFoundException(("Unable to find movie")));
        return movieDTO.getDuration()>170;
    }

    @Override
    public void delete(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new NotFoundException(("Unable to find schedule with id=" + id)));
        scheduleRepository.delete(schedule);
    }

    @Override
    public ScheduleDTO toDto(Schedule schedule) {
        return ScheduleDTO.builder()
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
    public Schedule toEntity(ScheduleDTO scheduleDTO) {
        Hall hall = hallService.convertToEntity(hallService.findByNameAndCinemaName(scheduleDTO.getHallName(), scheduleDTO.getCinemaName()));
        Movie movie = movieService.toEntity(movieService.findByTitle(scheduleDTO.getMovieTitle()).orElse(null));
        Schedule schedule = Schedule.builder()
                .id(scheduleDTO.getId())
                .date(scheduleDTO.getDate())
                .startTime(scheduleDTO.getStartTime())
//                .endTime(scheduleDto.getStartTime().plusMinutes(movie.getDuration()))
                .is3d(scheduleDTO.is3d())
                .isLongMovie(scheduleDTO.isLongMovie())
                .movie(movie)
                .hall(hall)
                .build();
        schedule.setEndTime(getEndTime(schedule));
        return schedule;
    }
}
