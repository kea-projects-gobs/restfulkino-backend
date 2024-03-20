package dk.kino.repository;

import dk.kino.entity.MoviePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePriceRepository extends JpaRepository<MoviePrice,String> {
}
