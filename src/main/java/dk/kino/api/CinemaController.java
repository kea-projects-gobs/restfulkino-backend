package dk.kino.api;

import dk.kino.dto.CinemaDTO;
import dk.kino.service.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/cinemas")
public class CinemaController {

    CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @Operation(summary = "Get all cinemas", description = "Returns a list of all cinemas.")
    @GetMapping
    public ResponseEntity<List<CinemaDTO>> getAllCinemas() {
        List<CinemaDTO> cinemas = cinemaService.findAll();
        return ResponseEntity.ok(cinemas);
    }

    @Operation(summary = "Get cinema by id", description = "Returns a single cinema, if one with the given id exists.")
    @GetMapping("/{id}")
    public ResponseEntity<CinemaDTO> findCinemaById(@PathVariable int id) {
        CinemaDTO cinema = cinemaService.findById(id);
        return ResponseEntity.ok(cinema);
    }

    @Operation(summary = "Get cinemas by movie id", description = "Returns a list of cinemas showing a movie with the given id.")
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CinemaDTO>> findCinemasByMovieId(@PathVariable int movieId) {
        List<CinemaDTO> cinemas = cinemaService.findCinemasByMovieId(movieId);
        return ResponseEntity.ok(cinemas);
    }

    @Operation(summary = "Add a cinema", description = "Creates a new cinema.")
    @PostMapping
    public ResponseEntity<CinemaDTO> addCinema(@RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO createCinema = cinemaService.createCinema(cinemaDTO);
        return ResponseEntity.ok(createCinema);
    }

    @Operation(summary = "Update a cinema", description = "Updates a cinema.")
    @PutMapping("/{id}")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable int id, @RequestBody CinemaDTO cinemaDTO) {
    CinemaDTO updatedCinema = cinemaService.updateCinema(id, cinemaDTO);
    if (updatedCinema != null) {
        return ResponseEntity.ok(updatedCinema);
    } else {
        return ResponseEntity.notFound().build();
    }
}
    @Operation(summary = "Delete a cinema", description = "Soft deletes a cinema, by flagging it as inactive.")
    @DeleteMapping("/{id}")
    public ResponseEntity<CinemaDTO> deleteCinema(@PathVariable int id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.ok().build();
    }
}
