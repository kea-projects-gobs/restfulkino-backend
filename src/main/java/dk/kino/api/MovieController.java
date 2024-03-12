package dk.kino.api;

import dk.kino.dto.MovieDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/movies")
public class MovieController {

   MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable int id) {
        MovieDTO movie = movieService.findById(id);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MovieDTO> addMovie(@RequestBody MovieDTO movieDTO) {
        MovieDTO createdMovie = movieService.createMovie(movieDTO);
        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable int id, @RequestBody MovieDTO movieDTO) {
        MovieDTO updatedMovie = movieService.updateMovie(id, movieDTO);
        if (updatedMovie != null) {
            return ResponseEntity.ok(updatedMovie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
