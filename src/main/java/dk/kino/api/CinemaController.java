package dk.kino.api;

import dk.kino.dto.CinemaDTO;
import dk.kino.service.cinema.CinemaService;
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

    @GetMapping
    public ResponseEntity<List<CinemaDTO>> getAllCinemas() {
        List<CinemaDTO> cinemas = cinemaService.findAll();
        return ResponseEntity.ok(cinemas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDTO> findCinemaById(@PathVariable int id) {
        CinemaDTO cinema = cinemaService.findById(id);
        return ResponseEntity.ok(cinema);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<CinemaDTO>> findCinemasByMovieId(@PathVariable int movieId) {
        List<CinemaDTO> cinemas = cinemaService.findCinemasByMovieId(movieId);
        return ResponseEntity.ok(cinemas);
    }

    @PostMapping
    public ResponseEntity<CinemaDTO> addCinema(@RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO createCinema = cinemaService.createCinema(cinemaDTO);
        return ResponseEntity.ok(createCinema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable int id, @RequestBody CinemaDTO cinemaDTO) {
    CinemaDTO updatedCinema = cinemaService.updateCinema(id, cinemaDTO);
    if (updatedCinema != null) {
        return ResponseEntity.ok(updatedCinema);
    } else {
        return ResponseEntity.notFound().build();
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<CinemaDTO> deleteCinema(@PathVariable int id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.ok().build();
    }
}
