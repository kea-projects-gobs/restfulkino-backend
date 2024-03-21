package dk.kino.service;

import dk.kino.dto.CinemaDTO;
import dk.kino.entity.Cinema;


import java.util.List;

public interface CinemaService {
    List<CinemaDTO> findAll();
    CinemaDTO findById(int id);
    CinemaDTO createCinema(CinemaDTO cinemaDTO);
    CinemaDTO updateCinema(int id, CinemaDTO cinemaDTO);
    void deleteCinema(int id);

    CinemaDTO convertToDTO(Cinema cinema);

    Cinema convertToEntity(CinemaDTO cinemaDTO);

    List<CinemaDTO> findCinemasByMovieId(int movieId);

}
