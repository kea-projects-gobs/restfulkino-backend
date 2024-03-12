package dk.kino.api;

import dk.kino.dto.MovieDTO;
import dk.kino.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

   private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable int id) {
        // TODO: Add proper exception
        return ResponseEntity.ok(movieService.findById(id).orElseThrow(() -> null));
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(movieDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable int id, @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok().body(movieService.update(id, movieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
