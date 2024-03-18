package dk.kino.api;


import dk.kino.dto.ScheduleDTO;
import dk.kino.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController (ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getSchedules() {
        return ResponseEntity.ok().body(scheduleService.findAll());
    }

    @GetMapping("/{date}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDate(@PathVariable("date") LocalDate date){
        return ResponseEntity.ok().body(scheduleService.findByDate(date));
    }

    @GetMapping("/{date}/movies/{movieId}/cinemas/{cinemasId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDateAndCinemaIdAndMovieId(
            @PathVariable("date") LocalDate date, @PathVariable("movieId") int movieId, @PathVariable("cinemasId") int cinemaId) {
        return ResponseEntity.ok().body(scheduleService.findByDateAndMovieIdAndCinemaId(date,movieId,cinemaId));
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(scheduleDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable int id, @RequestBody ScheduleDTO scheduleDto) {
        return ResponseEntity.ok().body(scheduleService.update(id,scheduleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
