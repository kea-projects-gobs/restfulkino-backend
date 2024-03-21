package dk.kino.service.impl;

import dk.kino.dto.MovieDTO;
import dk.kino.entity.Movie;
import dk.kino.repository.MovieRepository;
import dk.kino.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public MovieDTO create(MovieDTO movieDTO) {
        movieDTO.setActive(true);
        return toDto(movieRepository.save(toEntity(movieDTO)));
    }

    @Override
    public Optional<MovieDTO> findById(int id) {
        return movieRepository.findById(id)
                .filter(Movie::isActive)
                .map(this::toDto);
    }

    @Override
    public Optional<MovieDTO> findByTitle(String title) {
        return movieRepository.findByTitleAndIsActiveTrue(title).map(this::toDto);
    }

    @Override
    public List<MovieDTO> findAll() {
        return movieRepository.findAllByIsActiveTrue().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public MovieDTO update(int id, MovieDTO movieDTO) {
        // TODO: Add proper exception
        Movie originalMovie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException(("Unable to find movie")));
        Movie movie = toEntity(movieDTO);

        // Update
        originalMovie.setTitle(movie.getTitle());
        originalMovie.setDescription(movie.getDescription());
        originalMovie.setReleaseDate(movie.getReleaseDate());
        originalMovie.setDuration(movie.getDuration());
        originalMovie.setImageUrl(movie.getImageUrl());
        originalMovie.setLanguage(movie.getLanguage());
        originalMovie.setGenre(movie.getGenre());
        originalMovie.setDirector(movie.getDirector());
        originalMovie.setCast(movie.getCast());

        return toDto(movieRepository.save(originalMovie));
    }

    @Override
    public void delete(int id) {
        // TODO: Add proper exception
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found."));
        movie.setActive(false);
        movieRepository.save(movie);
    }

    @Override
    public MovieDTO toDto(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .duration(movie.getDuration())
                .imageUrl(movie.getImageUrl())
                .language(movie.getLanguage())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .cast(movie.getCast())
                .isActive(movie.isActive())
                .build();
    }

    @Override
    public Movie toEntity(MovieDTO movieDTO) {
        return Movie.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .description(movieDTO.getDescription())
                .releaseDate(movieDTO.getReleaseDate())
                .duration(movieDTO.getDuration())
                .imageUrl(movieDTO.getImageUrl())
                .language(movieDTO.getLanguage())
                .genre(movieDTO.getGenre())
                .director(movieDTO.getDirector())
                .cast(movieDTO.getCast())
                .isActive(movieDTO.isActive())
                .build();
    }
}
