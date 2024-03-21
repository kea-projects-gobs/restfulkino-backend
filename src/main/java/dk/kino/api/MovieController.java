package dk.kino.api;

import dk.kino.dto.MovieDTO;
import dk.kino.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all movies", description = "Returns a list of all movies.")
    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @Operation(summary = "Get movie by id", description = "Returns a single movie, if one with the given id exists.")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable int id) {
        // TODO: Add proper exception
        return ResponseEntity.ok(movieService.findById(id).orElseThrow(() -> null));
    }

    @Operation(summary = "Add movie", description = "Creates a new movie")
    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.create(movieDTO));
    }

    @Operation(summary = "Update a movie", description = "Updates a movie.")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable int id, @RequestBody MovieDTO movieDTO) {
        return ResponseEntity.ok().body(movieService.update(id, movieDTO));
    }

    @Operation(summary = "Delete a movie", description = "Soft deletes a movie, by flagging it as inactive.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
