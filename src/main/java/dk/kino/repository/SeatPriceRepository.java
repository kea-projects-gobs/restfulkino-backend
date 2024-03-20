package dk.kino.repository;

import dk.kino.entity.SeatPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatPriceRepository extends JpaRepository<SeatPrice,String> {
}
