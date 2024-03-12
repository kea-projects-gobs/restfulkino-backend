package dk.kino.service.cinema;

import dk.kino.dto.CinemaDTO;
import dk.kino.dto.HallDTO;
import dk.kino.entity.Cinema;
import dk.kino.entity.Hall;
import dk.kino.repository.CinemaRepository;
import dk.kino.service.hall.HallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    private CinemaRepository cinemaRepository;
    private HallService hallService;

    public CinemaServiceImpl(CinemaRepository cinemaRepository, HallService hallService) {
        this.cinemaRepository = cinemaRepository;
        this.hallService = hallService;
    }
    @Override
    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CinemaDTO findById(int id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        return cinema.map(this::convertToDTO).orElse(null);
    }

    @Override
    public CinemaDTO createCinema(CinemaDTO cinemaDTO) {
        Cinema cinema = convertToEntity(cinemaDTO);
        Cinema savedCinema = cinemaRepository.save(cinema);
        return convertToDTO(savedCinema);
    }

    @Override
    public CinemaDTO updateCinema(int id, CinemaDTO cinemaDTO) {
        Optional<Cinema> existingCinema = cinemaRepository.findById(id);
        if (existingCinema.isPresent()) {
            Cinema cinema = existingCinema.get();
            // Update the entity's fields here
            cinema.setName(cinemaDTO.getName());
            cinema.setCity(cinemaDTO.getCity());
            cinema.setStreet(cinemaDTO.getStreet());
            cinema.setDescription(cinemaDTO.getDescription());
            cinema.setPhone(cinemaDTO.getPhone());
            cinema.setEmail(cinemaDTO.getEmail());
            cinema.setImageUrl(cinemaDTO.getImageUrl());
            cinema = cinemaRepository.save(cinema);
            return convertToDTO(cinema);
        } else {
            return null; // Or throw an exception if preferred
        }
    }

    @Override
    public void deleteCinema(int id) {
        cinemaRepository.deleteById(id);
    }

    @Override
    public CinemaDTO convertToDTO(Cinema cinema) {
        CinemaDTO dto = new CinemaDTO();
        dto.setId(cinema.getId());
        dto.setName(cinema.getName());
        dto.setCity(cinema.getCity());
        dto.setStreet(cinema.getStreet());
        dto.setDescription(cinema.getDescription());
        dto.setPhone(cinema.getPhone());
        dto.setEmail(cinema.getEmail());
        dto.setImageUrl(cinema.getImageUrl());
        // convert halls to HallDTOs and set them
        if (cinema.getHalls() != null){
            List<HallDTO> hallDTOs = cinema.getHalls().stream().map(hall -> (HallDTO) hallService.convertHallToDTO(hall)).collect(Collectors.toList());
            dto.setHalls(hallDTOs);
        }
        return dto;
    }

    
    @Override
    public Cinema convertToEntity(CinemaDTO cinemaDTO) {
        Cinema cinema = new Cinema();
        cinema.setName(cinemaDTO.getName());
        cinema.setCity(cinemaDTO.getCity());
        cinema.setStreet(cinemaDTO.getStreet());
        cinema.setDescription(cinemaDTO.getDescription());
        cinema.setPhone(cinemaDTO.getPhone());
        cinema.setEmail(cinemaDTO.getEmail());
        cinema.setImageUrl(cinemaDTO.getImageUrl());
        return cinema;
    }
}
