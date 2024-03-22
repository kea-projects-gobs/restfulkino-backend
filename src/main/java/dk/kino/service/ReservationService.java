package dk.kino.service;

import dk.kino.dto.ReservationPriceCalcDTO;
import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.dto.SeatDTO;
import dk.kino.entity.Reservation;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing reservations.
 */
public interface ReservationService {

    /**
     * Calculates the price for a reservation based on the provided reservation request without persistence.
     *
     * @param reservationReqDTO The reservation request DTO containing necessary details for the reservation.
     * @return A DTO containing the calculated reservation price.
     */
    ReservationPriceCalcDTO calculatePrice(ReservationReqDTO reservationReqDTO);

    /**
     * Creates a reservation based on the provided reservation request.
     *
     * @param reservationReqDTO The reservation request DTO containing necessary details for the reservation.
     * @param principal         The principal representing the current user.
     * @return A DTO representing the created reservation.
     */
    ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO, Principal principal);

    /**
     * Finds a reservation by its unique identifier.
     *
     * @param id The unique identifier of the reservation.
     * @return An optional containing the DTO representing the found reservation, or empty if not found.
     */
    Optional<ReservationResDTO> findReservationById(int id);

    /**
     * Finds all reserved seats for a specific schedule.
     *
     * @param scheduleId The unique identifier of the schedule.
     * @return A list of DTOs representing the reserved seats for the specified schedule.
     */
    List<SeatDTO> findAllReservedSeatsByScheduleId(int scheduleId);

    /**
     * Converts a reservation entity to its corresponding DTO representation.
     *
     * @param reservation The reservation entity to convert.
     * @return The DTO representing the reservation.
     */
    ReservationResDTO toDto(Reservation reservation);

    /**
     * Converts a reservation request DTO to its corresponding entity representation.
     *
     * @param reservationReqDTO The reservation request DTO to convert.
     * @return The entity representing the reservation.
     */
    Reservation toEntity(ReservationReqDTO reservationReqDTO);
}
