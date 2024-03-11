package dk.kino.repository;

import dk.kino.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {
    public Optional<Cinema> findByName(String name);
}
