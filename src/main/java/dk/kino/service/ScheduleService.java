package dk.kino.service;

import dk.kino.dto.ScheduleDTO;
import dk.kino.entity.Schedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing schedules.
 */
public interface ScheduleService {

    /**
     * Retrieves all schedules.
     *
     * @return A list of DTOs representing all schedules.
     */
    List<ScheduleDTO> findAll();

    /**
     * Finds a schedule by its unique identifier.
     *
     * @param id The unique identifier of the schedule.
     * @return An optional containing the DTO representing the found schedule, or empty if not found.
     */
    Optional<ScheduleDTO> findById(int id);

    /**
     * Finds schedules for a specific date.
     *
     * @param date The date for which schedules are to be retrieved.
     * @return A list of DTOs representing schedules for the specified date.
     */
    List<ScheduleDTO> findByDate(LocalDate date);

    /**
     * Finds schedules for a specific date, movie, and cinema.
     *
     * @param date     The date for which schedules are to be retrieved.
     * @param movieId  The unique identifier of the movie.
     * @param cinemaId The unique identifier of the cinema.
     * @return A list of DTOs representing schedules for the specified date, movie, and cinema.
     */
    List<ScheduleDTO> findByDateAndMovieIdAndCinemaId(LocalDate date, int movieId, int cinemaId);

    /**
     * Creates a new schedule.
     *
     * @param scheduleDTO The DTO containing details for the schedule to be created.
     * @return The DTO representing the created schedule.
     */
    ScheduleDTO create(ScheduleDTO scheduleDTO);

    /**
     * Updates an existing schedule.
     *
     * @param id          The unique identifier of the schedule to be updated.
     * @param scheduleDTO The DTO containing updated details for the schedule.
     * @return The DTO representing the updated schedule.
     */
    ScheduleDTO update(int id, ScheduleDTO scheduleDTO);

    /**
     * Deletes a schedule by its unique identifier.
     *
     * @param id The unique identifier of the schedule to be deleted.
     */
    void delete(int id);

    /**
     * Converts a schedule entity to its corresponding DTO representation.
     *
     * @param schedule The schedule entity to convert.
     * @return The DTO representing the schedule.
     */
    ScheduleDTO toDto(Schedule schedule);

    /**
     * Converts a schedule DTO to its corresponding entity representation.
     *
     * @param scheduleDTO The DTO to convert.
     * @return The entity representing the schedule.
     */
    Schedule toEntity(ScheduleDTO scheduleDTO);
}

