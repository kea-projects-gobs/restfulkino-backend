package dk.kino.service;

import dk.kino.dto.MovieDTO;
import dk.kino.entity.Movie;

import java.util.List;
import java.util.Optional;

/**
 * Interface for movie-related operations.
 */

public interface MovieService {

    /**
     * Creates a new movie.
     *
     * @param movieDTO The <code>Movie</code> object to create.
     * @return The created <code>Movie</code> object.
     */
    MovieDTO create(MovieDTO movieDTO);

    /**
     * Finds a <code>Movie</code> by its id.
     * Only active movies are returned.
     *
     * @param id The id of the <code>Movie</code> object to find.
     * @return The <code>Movie</code> with the given id.
     */
    Optional<MovieDTO> findById(int id);

    /**
     * Finds a <code>Movie</code> by its title.
     * Only active movies are returned.
     *
     * @param title The title of the <code>Movie</code> object to find.
     * @return The <code>Movie</code> with the given title.
     */
    Optional<MovieDTO> findByTitle(String title);

    /**
     * Finds all movies.
     * Only active movies are returned.
     *
     * @return List of all movies.
     */
    List<MovieDTO> findAll();

    /**
     * Updates a movie.
     *
     * @param id The id of the <code>Movie</code> object to update.
     * @param movieDTO The updated movie.
     * @return The updated movie.
     */
    MovieDTO update(int id, MovieDTO movieDTO);

    /**
     * Deletes a movie by setting it as inactive.
     *
     * @param id The id of the <code>Movie</code> object to delete.
     */
    void delete(int id);

    /**
     * Converts a <code>MovieDTO</code> object to a <code>Movie</code> object.
     * @param movieDTO The <code>MovieDTO</code> object to convert.
     * @return The converted <code>Movie</code> object.
     */
    Movie toEntity(MovieDTO movieDTO);

    /**
     * Converts a <code>Movie</code> object to a <code>MovieDTO</code> object.
     * @param movie The <code>Movie</code> object to convert.
     * @return The converted <code>MovieDTO</code> object.
     */
    MovieDTO toDto(Movie movie);
}
