package dk.kino.service.hall;

import dk.kino.dto.HallDTO;
import dk.kino.entity.Hall;
import dk.kino.repository.CinemaRepository;
import dk.kino.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {

    private HallRepository hallRepository;
    private CinemaRepository cinemaRepository;

    public HallServiceImpl(HallRepository hallRepository, CinemaRepository cinemaRepository) {
        this.hallRepository = hallRepository;
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public List<HallDTO> findAll() {
        return hallRepository.findAll().stream().map(this::convertHallToDTO).collect(Collectors.toList());
    }

    @Override
    public HallDTO findById(int id) {
        Optional<Hall> hall = hallRepository.findById(id);
        return hall.map(this::convertHallToDTO).orElse(null);
    }

    @Override
    public HallDTO findByNameAndCinemaName(String hallName, String cinemaName) {
        Optional<Hall> hall = hallRepository.findByNameAndCinemaName(hallName,cinemaName);
        return hall.map(this::convertHallToDTO).orElse(null);
    }


    @Override
    public HallDTO createHall(HallDTO hallDTO) {
        Hall hall = convertToEntity(hallDTO);
        cinemaRepository.findById(hallDTO.getCinemaId()).ifPresent(hall::setCinema);
        Hall savedHall = hallRepository.save(hall);
        return convertHallToDTO(savedHall);
    }

    @Override
    public HallDTO updateHall(int id, HallDTO hallDTO) {
        Optional<Hall> existingHall = hallRepository.findById(id);
        if (existingHall.isPresent()){
            Hall hall = existingHall.get();
            // Update the entity's fields:
            hall.setName(hallDTO.getName());
            hall.setNoOfRows(hallDTO.getNoOfRows());
            hall.setNoOfColumns(hallDTO.getNoOfColumns());
            hall.setImageUrl(hallDTO.getImageUrl());
            cinemaRepository.findById(hallDTO.getCinemaId()).ifPresent(hall::setCinema);
            hall = hallRepository.save(hall);
            return convertHallToDTO(hall);
        } else {
            return null;
        }
    }

    @Override
    public void deleteHall(int id) {
        hallRepository.deleteById(id);
    }

    @Override
    public HallDTO convertHallToDTO(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setNoOfRows(hall.getNoOfRows());
        dto.setNoOfColumns(hall.getNoOfColumns());
        dto.setImageUrl(hall.getImageUrl());
        if (hall.getCinema() != null){
            dto.setCinemaId(hall.getCinema().getId());
        }
        return dto;
    }

    @Override
    public Hall convertToEntity(HallDTO hallDTO) {
        Hall hall = new Hall();
        hall.setId(hallDTO.getId());
        hall.setName(hallDTO.getName());
        hall.setNoOfRows(hallDTO.getNoOfRows());
        hall.setNoOfColumns(hallDTO.getNoOfColumns());
        hall.setImageUrl(hallDTO.getImageUrl());
        hall.setCinema(cinemaRepository.findById(hallDTO.getCinemaId()).orElse(null));
        return hall;
    }
}
