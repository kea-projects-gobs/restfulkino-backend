package dk.kino.service;

import dk.kino.dto.MovieDTO;
import dk.kino.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    MovieDTO create(MovieDTO movieDTO);
    Optional<MovieDTO> findById(int id);
    Optional<MovieDTO> findByTitle(String title);
    List<MovieDTO> findAll();
    MovieDTO update(int id, MovieDTO movieDTO);
    void delete(int id);
    Movie toEntity(MovieDTO movieDTO);
}
