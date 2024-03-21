package dk.kino.repository;

import dk.kino.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Integer> {
    Optional<Hall> findByNameAndCinemaNameAndIsActiveTrue(String name, String cinemaName);
    List<Hall> findByCinemaIdAndIsActiveTrue(int cinemaId);
    List<Hall> findAllByIsActiveTrue();
}
