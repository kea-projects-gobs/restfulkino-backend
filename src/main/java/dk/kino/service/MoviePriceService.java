package dk.kino.service;

import dk.kino.entity.MoviePrice;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing movie prices.
 */
public interface MoviePriceService {

    /**
     * Finds a movie price by its name.
     *
     * @param name The name of the movie price to find.
     * @return An optional containing the movie price if found, or empty if not found.
     */
    Optional<MoviePrice> findMoviePriceByName(String name);

    /**
     * Retrieves all movie prices.
     *
     * @return A list of all movie prices.
     */
    List<MoviePrice> findAllMoviePrices();

    /**
     * Creates a new movie price.
     *
     * @param moviePrice The movie price to create.
     * @return The created movie price.
     */
    MoviePrice createMoviePrice(MoviePrice moviePrice);

    /**
     * Updates an existing movie price.
     *
     * @param name       The name of the movie price to update.
     * @param moviePrice The updated movie price details.
     * @return The updated movie price.
     */
    MoviePrice updateMoviePrice(String name, MoviePrice moviePrice);
}

