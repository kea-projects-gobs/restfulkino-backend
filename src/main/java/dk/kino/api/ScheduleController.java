package dk.kino.api;


import dk.kino.entity.Schedule;
import dk.kino.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController (ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules() {
        return ResponseEntity.ok().body(scheduleService.findAll());
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<Schedule>> getSchedulesByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok().body(scheduleService.findByDate(date));
    }

    @GetMapping("/{date}/movies/{movieId}")
    public ResponseEntity<List<Schedule>> getSchedulesByDateAndMovieId(@PathVariable LocalDate date, @PathVariable int movieId) {
        return ResponseEntity.ok().body(scheduleService.findByDateAndMovieId(date,movieId));
    }

    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(schedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable int id, @RequestBody Schedule schedule) {
        return ResponseEntity.ok().body(scheduleService.update(id,schedule));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
