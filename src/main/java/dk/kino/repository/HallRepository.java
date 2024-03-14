package dk.kino.repository;

import dk.kino.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Integer> {
    Optional<Hall> findByNameAndCinemaName(String name, String cinemaName);
}
