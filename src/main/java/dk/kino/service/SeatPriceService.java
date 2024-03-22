package dk.kino.service;

import dk.kino.entity.SeatPrice;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing seat prices.
 */
public interface SeatPriceService {

    /**
     * Finds a seat price by its name.
     *
     * @param name The name of the seat price to find.
     * @return An optional containing the seat price if found, or empty if not found.
     */
    Optional<SeatPrice> findSeatPriceByName(String name);

    /**
     * Retrieves all seat prices.
     *
     * @return A list of all seat prices.
     */
    List<SeatPrice> findAllSeatPrices();

    /**
     * Creates a new seat price.
     *
     * @param seatPrice The seat price to create.
     * @return The created seat price.
     */
    SeatPrice createSeatPrice(SeatPrice seatPrice);

    /**
     * Updates an existing seat price.
     *
     * @param name      The name of the seat price to update.
     * @param seatPrice The updated seat price details.
     * @return The updated seat price.
     */
    SeatPrice updateSeatPrice(String name, SeatPrice seatPrice);
}

