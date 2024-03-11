package dk.kino.service.cinema;

import dk.kino.dto.CinemaDTO;
import dk.kino.entity.Cinema;


import java.util.List;

public interface CinemaService {
    List<CinemaDTO> findAll();
    //CinemaDTO findByName(String name);
    CinemaDTO findById(int id);
    CinemaDTO createCinema(CinemaDTO cinemaDTO);
    CinemaDTO updateCinema(int id, CinemaDTO cinemaDTO);
    void deleteCinema(int id);

    Object convertToDTO(Cinema cinema);

    Cinema convertToEntity(CinemaDTO cinemaDTO);


}
