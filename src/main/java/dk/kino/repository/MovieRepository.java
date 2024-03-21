package dk.kino.repository;

import dk.kino.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    public Optional<Movie> findByTitleAndIsActiveTrue(String title);
    public List<Movie> findAllByIsActiveTrue();
}
