package dk.kino.service;

import dk.kino.dto.HallDTO;
import dk.kino.entity.Hall;

import java.util.List;

public interface HallService {

    List<HallDTO> findAll();
    HallDTO findById(int id);
    HallDTO findByNameAndCinemaName(String hallName, String cinemaName);
    HallDTO createHall(HallDTO hallDTO);
    HallDTO updateHall(int id, HallDTO hallDTO);
    void deleteHall(int id);
    HallDTO convertHallToDTO(Hall hall);
    Hall convertToEntity(HallDTO hallDTO);
    List<HallDTO> findHallsByCinemaId(int cinemaId);
}
