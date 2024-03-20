package dk.kino.service;

import dk.kino.entity.SeatPrice;

import java.util.List;
import java.util.Optional;

public interface SeatPriceService {
    Optional<SeatPrice> findByName(String name);
    List<SeatPrice> findAll();
    SeatPrice createSeatPrice(SeatPrice seatPrice);
    SeatPrice updatePrices(String name,SeatPrice seatPrice);
}
