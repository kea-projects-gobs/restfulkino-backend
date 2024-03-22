package dk.kino.service.impl;

import dk.kino.dto.CinemaDTO;
import dk.kino.dto.HallDTO;
import dk.kino.entity.Cinema;
import dk.kino.entity.Hall;
import dk.kino.entity.Schedule;
import dk.kino.repository.CinemaRepository;
import dk.kino.repository.ScheduleRepository;
import dk.kino.service.CinemaService;
import dk.kino.service.HallService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final HallService hallService;
    private final ScheduleRepository scheduleRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository, HallService hallService, ScheduleRepository scheduleRepository) {
        this.cinemaRepository = cinemaRepository;
        this.hallService = hallService;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAllByIsActiveTrue().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CinemaDTO findById(int id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id)
        .filter(Cinema::isActive);
        return cinema.map(this::convertToDTO).orElseThrow(() -> new RuntimeException("Cinema not found"));
    }

    @Override
    public CinemaDTO createCinema(CinemaDTO cinemaDTO) {
        Cinema cinema = convertToEntity(cinemaDTO);
        cinema.setActive(true);
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
        Cinema cinema = cinemaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cinema not found"));
        cinema.setActive(false);
        cinemaRepository.save(cinema);

        // Soft delete all related halls
        List<Hall> halls = cinema.getHalls();
        if (halls != null){
            halls.forEach(hall -> {
                hallService.deleteHall(hall.getId());
            });
        }
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
        dto.setActive(cinema.isActive());
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
        cinema.setActive(cinemaDTO.isActive());
        return cinema;
    }

    @Override
    public List<CinemaDTO> findCinemasByMovieId(int movieId) {
        List<Schedule> schedules = scheduleRepository.findByMovieId(movieId);
        Set<Cinema> cinemas = schedules.stream().map(schedule -> schedule.getHall().getCinema())
        .collect(Collectors.toSet());
        return cinemas.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
