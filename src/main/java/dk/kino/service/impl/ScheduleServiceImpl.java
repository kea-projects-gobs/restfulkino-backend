package dk.kino.service.impl;

import dk.kino.entity.Schedule;
import dk.kino.repository.ScheduleRepository;
import dk.kino.service.ScheduleService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> findByDate(LocalDate date) {
        return scheduleRepository.findByDate(date);
    }

    @Override
    public List<Schedule> findByDateAndMovieId(LocalDate date, int movieId) {
        return null;
    }

    @Override
    public Schedule create(Schedule schedule) {
        // TODO: Add Validation to the schedule object
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(int id,Schedule schedule) {
        // Find schedule
        Schedule originalSchedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));

        // Update original schedule
        originalSchedule.setDate(schedule.getDate());
        originalSchedule.setStartTime(schedule.getStartTime());
        originalSchedule.set3d(schedule.is3d());
        originalSchedule.setHelaften(schedule.isHelaften());

        // Persist
        return scheduleRepository.save(originalSchedule);
    }

    @Override
    public void delete(int id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find schedule with id=" + id)));
        scheduleRepository.delete(schedule);
    }
}
