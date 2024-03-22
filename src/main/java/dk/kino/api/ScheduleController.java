package dk.kino.api;


import dk.kino.dto.ScheduleDTO;
import dk.kino.exception.NotFoundException;
import dk.kino.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all schedules", description = "Returns a list of all schedules.")
    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getSchedules() {
        return ResponseEntity.ok().body(scheduleService.findAll());
    }

    @Operation(summary = "Get schedule by id", description = "Returns a single schedule, if one with the given id exists.")
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleDTO> getSchedulesById(@PathVariable("id") int id){
        return ResponseEntity.ok().body(scheduleService.findById(id).orElseThrow(() -> new NotFoundException("Schedule not found")));
    }

    @Operation(summary = "Get schedules by date, movie and cinema", description = "Returns a list of schedules for a specific date, movie id and cinema id.")
    @GetMapping("/{date}/movies/{movieId}/cinemas/{cinemasId}")
    public ResponseEntity<List<ScheduleDTO>> getSchedulesByDateAndCinemaIdAndMovieId(
            @PathVariable("date") LocalDate date, @PathVariable("movieId") int movieId, @PathVariable("cinemasId") int cinemaId) {
        return ResponseEntity.ok().body(scheduleService.findByDateAndMovieIdAndCinemaId(date,movieId,cinemaId));
    }

    @Operation(summary = "Create a schedule", description = "Creates a new schedule.")
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.create(scheduleDTO));
    }

    @Operation(summary = "Update a schedule", description = "Updates a schedule.")
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleDTO> updateSchedule(@PathVariable int id, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok().body(scheduleService.update(id, scheduleDTO));
    }

    @Operation(summary = "Delete a schedule", description = "Deletes a schedule.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable int id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
