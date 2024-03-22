package dk.kino.service;

import dk.kino.entity.ReservationPrice;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing reservation prices.
 */
public interface ReservationPriceService {

    /**
     * Finds a reservation price by its name.
     *
     * @param name The name of the reservation price to find.
     * @return An optional containing the reservation price if found, or empty if not found.
     */
    Optional<ReservationPrice> findReservationPriceByName(String name);

    /**
     * Retrieves all reservation prices.
     *
     * @return A list of all reservation prices.
     */
    List<ReservationPrice> findAllReservationPrices();

    /**
     * Creates a new reservation price.
     *
     * @param reservationPrice The reservation price to create.
     * @return The created reservation price.
     */
    ReservationPrice createReservationPrice(ReservationPrice reservationPrice);

    /**
     * Updates an existing reservation price.
     *
     * @param name             The name of the reservation price to update.
     * @param reservationPrice The updated reservation price details.
     * @return The updated reservation price.
     */
    ReservationPrice updateReservationPrice(String name, ReservationPrice reservationPrice);
}

