package dk.kino.api;


import dk.kino.dto.ScheduleDto;
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
    public ResponseEntity<List<ScheduleDto>> getSchedules() {
        return ResponseEntity.ok().body(scheduleService.findAll());
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByDate(@PathVariable LocalDate date) {
        return ResponseEntity.ok().body(scheduleService.findByDate(date));
    }

    @GetMapping("/{date}/movies/{movieId}")
    public ResponseEntity<List<ScheduleDto>> getSchedulesByDateAndMovieId(@PathVariable LocalDate date, @PathVariable int movieId) {
        return ResponseEntity.ok().body(scheduleService.findByDateAndMovieId(date,movieId));
    }

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(scheduleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDto> updateSchedule(@PathVariable int id, @RequestBody ScheduleDto scheduleDto) {
        return ResponseEntity.ok().body(scheduleService.update(id,scheduleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
