package dk.kino.service;

import dk.kino.entity.SeatPrice;

import java.util.List;
import java.util.Optional;

public interface SeatPriceService {
    Optional<SeatPrice> findSeatPriceByName(String name);
    List<SeatPrice> findAllSeatPrices();
    SeatPrice createSeatPrice(SeatPrice seatPrice);
    SeatPrice updateSeatPrice(String name, SeatPrice seatPrice);

}
