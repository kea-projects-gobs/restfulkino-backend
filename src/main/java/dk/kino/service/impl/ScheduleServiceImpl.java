package dk.kino.service.impl;

import dk.kino.dto.ScheduleDto;
import dk.kino.entity.Schedule;
import dk.kino.repository.ScheduleRepository;
import dk.kino.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
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
        // TODO: Add Validation to the schedule object
        return toDto(scheduleRepository.save(toEntity(scheduleDto)));
    }

    @Override
    public ScheduleDto update(int id,ScheduleDto scheduleDto) {
        // Find schedule
        Schedule originalSchedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));

        // Map to entity
        Schedule schedule = toEntity(scheduleDto);

        // Update original schedule
        originalSchedule.setDate(schedule.getDate());
        originalSchedule.setStartTime(schedule.getStartTime());
        originalSchedule.set3d(schedule.is3d());
        originalSchedule.setHelaften(schedule.isHelaften());

        // Persist
        return toDto(scheduleRepository.save(originalSchedule));
    }

    @Override
    public void delete(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));
        scheduleRepository.delete(schedule);
    }

    private ScheduleDto toDto(Schedule schedule) {
        return ScheduleDto.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .startTime(schedule.getStartTime())
                .is3d(schedule.is3d())
                .isHelaften(schedule.isHelaften())
                .build();
    }

    private Schedule toEntity(ScheduleDto scheduleDto) {
        return Schedule.builder()
                .id(scheduleDto.getId())
                .date(scheduleDto.getDate())
                .startTime(scheduleDto.getStartTime())
                .is3d(scheduleDto.is3d())
                .isHelaften(scheduleDto.isHelaften())
                .build();
    }
}
