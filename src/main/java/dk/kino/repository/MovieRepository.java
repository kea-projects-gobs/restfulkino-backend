package dk.kino.repository;

import dk.kino.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findByTitleAndIsActiveTrue(String title);
    List<Movie> findAllByIsActiveTrue();
}
