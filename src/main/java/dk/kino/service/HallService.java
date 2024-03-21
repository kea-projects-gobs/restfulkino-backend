package dk.kino.service;

import dk.kino.dto.HallDTO;
import dk.kino.entity.Hall;

import java.util.List;

/**
 * Interface for hall-related operations.
 */
public interface HallService {

    /**
     * Finds all halls.
     *
     * @return List of all halls.
     */
    List<HallDTO> findAll();

    /**
     * Finds a <code>Hall</code> by its id.
     *
     * @param id The id of the <code>Hall</code> object to find.
     * @return The <code>Hall</code> with the given id.
     */
    HallDTO findById(int id);

    /**
     * Finds a <code>Hall</code> by its name and cinema name.
     *
     * @param hallName The name of the <code>Hall</code> object to find.
     * @param cinemaName The name of the <code>Cinema</code> object.
     * @return The <code>Hall</code> with the given name and cinema name.
     */
    HallDTO findByNameAndCinemaName(String hallName, String cinemaName);

    /**
     * Creates a new hall.
     *
     * @param hallDTO The <code>Hall</code> object to create.
     * @return The created <code>Hall</code> object.
     */
    HallDTO createHall(HallDTO hallDTO);

    /**
     * Updates a hall.
     *
     * @param id The id of the <code>Hall</code> object to update.
     * @param hallDTO The updated hall.
     * @return The updated hall.
     */
    HallDTO updateHall(int id, HallDTO hallDTO);

    /**
     * Deletes a hall.
     *
     * @param id The id of the <code>Hall</code> object to delete.
     */
    void deleteHall(int id);

    /**
     * Converts a <code>Hall</code> object to a <code>HallDTO</code> object.
     *
     * @param hall The <code>Hall</code> object to convert.
     * @return The converted <code>HallDTO</code> object.
     */
    HallDTO convertHallToDTO(Hall hall);

    /**
     * Converts a <code>HallDTO</code> object to a <code>Hall</code> object.
     *
     * @param hallDTO The <code>HallDTO</code> object to convert.
     * @return The converted <code>Hall</code> object.
     */
    Hall convertToEntity(HallDTO hallDTO);

    /**
     * Finds all halls by cinema id.
     *
     * @param cinemaId The id of the cinema.
     * @return List of all halls with the given cinema id.
     */
    List<HallDTO> findHallsByCinemaId(int cinemaId);
}
