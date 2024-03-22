package dk.kino.service;

import dk.kino.dto.SeatDTO;
import dk.kino.entity.Seat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Interface representing a service for managing seats.
 */
public interface SeatService {

    /**
     * Finds a seat by its unique identifier.
     *
     * @param id The unique identifier of the seat.
     * @return An optional containing the DTO representing the found seat, or empty if not found.
     */
    Optional<SeatDTO> findSeatById(int id);

    /**
     * Finds all seats for a specific hall.
     *
     * @param hallId The unique identifier of the hall.
     * @return A list of DTOs representing all seats for the specified hall.
     */
    List<SeatDTO> findSeatsByHallId(int hallId);

    /**
     * Creates a new seat.
     *
     * @param seatDTO The DTO containing details for the seat to be created.
     * @return The DTO representing the created seat.
     */
    SeatDTO createSeat(SeatDTO seatDTO);

    /**
     * Creates multiple seats.
     *
     * @param seatDTOs The set of DTOs containing details for the seats to be created.
     * @return A list of DTOs representing the created seats.
     */
    List<SeatDTO> createSeats(Set<SeatDTO> seatDTOs);

    /**
     * Deletes multiple seats.
     *
     * @param seatDTOs The set of DTOs representing the seats to be deleted.
     */
    void deleteSeats(Set<SeatDTO> seatDTOs);

    /**
     * Converts a seat entity to its corresponding DTO representation.
     *
     * @param entity The seat entity to convert.
     * @return The DTO representing the seat.
     */
    SeatDTO toDto(Seat entity);

    /**
     * Converts a seat DTO to its corresponding entity representation.
     *
     * @param dto The DTO to convert.
     * @return The entity representing the seat.
     */
    Seat toEntity(SeatDTO dto);
}

