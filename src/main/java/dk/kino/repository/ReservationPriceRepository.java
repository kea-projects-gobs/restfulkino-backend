package dk.kino.repository;

import dk.kino.entity.ReservationPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationPriceRepository extends JpaRepository<ReservationPrice,String> {
}
