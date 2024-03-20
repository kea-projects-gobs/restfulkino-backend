package dk.kino.service;

import dk.kino.entity.MoviePrice;

import java.util.List;
import java.util.Optional;

public interface MoviePriceService {
    Optional<MoviePrice> findMoviePriceByName(String name);
    List<MoviePrice> findAllMoviePrices();
    MoviePrice createMoviePrice(MoviePrice moviePrice);
    MoviePrice updateMoviePrice(String name, MoviePrice moviePrice);
}
