package dk.kino.service;

import dk.kino.entity.ReservationPrice;

import java.util.List;
import java.util.Optional;

public interface ReservationPriceService {
    Optional<ReservationPrice> findReservationPriceByName(String name);
    List<ReservationPrice> findAllReservationPrices();
    ReservationPrice createReservationPrice(ReservationPrice reservationPrice);
    ReservationPrice updateReservationPrice(String name, ReservationPrice reservationPrice);
}
