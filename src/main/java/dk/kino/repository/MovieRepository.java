package dk.kino.repository;

import dk.kino.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    public Optional<Movie> findByTitle(String title);
}
