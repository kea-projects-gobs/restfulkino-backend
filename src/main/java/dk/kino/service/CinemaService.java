package dk.kino.service;

import dk.kino.dto.CinemaDTO;
import dk.kino.entity.Cinema;


import java.util.List;

/**
 * Interface for cinema-related operations.
 * 
 */

public interface CinemaService {
    /**
     * Finds all cinemas.
     * Only active cinemas are returned.
     * 
     * 
     * @return List of all cinemas.
     */
    List<CinemaDTO> findAll();

    /**
     * Finds a <code>Cinema</code> by its id.
     * Only active cinemas are returned.
     * 
     * @param id The id of the <code>Cinema</code> object to find.
     * @return The <code>Cinema</code> with the given id.
     */
    CinemaDTO findById(int id);


    /**
     * Creates a new cinema.
     *
     * @param cinemaDTO The <code>Cinema</code> object to create.
     * @return The created <code>Cinema</code> object.
     */
    CinemaDTO createCinema(CinemaDTO cinemaDTO);

    /**
     * Updates a cinema.
     * 
     * @param id The id of the <code>Cinema</code> object to update.
     * @param cinemaDTO The updated cinema.
     * @return The updated cinema.
     */
    CinemaDTO updateCinema(int id, CinemaDTO cinemaDTO);

    /**
     * Deletes a cinema by setting it as inactive. All related halls are also set as inactive.
     * 
     *
     * @param id The id of the <code>Cinema</code> object to delete.
     */
    void deleteCinema(int id);

    /**
     * Converts a <code>Cinema</code> object to a <code>CinemaDTO</code> object.
     * @param cinema The <code>Cinema</code> object to convert.
     * @return The converted <code>CinemaDTO</code> object.
     */
    CinemaDTO convertToDTO(Cinema cinema);

    /**
     * Converts a <code>CinemaDTO</code> object to a <code>Cinema</code> object.
     * @param cinemaDTO The <code>CinemaDTO</code> object to convert.
     * @return The converted <code>Cinema</code> object.
     */
    Cinema convertToEntity(CinemaDTO cinemaDTO);

    /**
     * Finds all cinemas by movieId.
     * 
     * @param movieId The id of the <code>Movie</code> object.
     * @return List of all cinemas showing the movie with the given id.
     */
    List<CinemaDTO> findCinemasByMovieId(int movieId);

}
