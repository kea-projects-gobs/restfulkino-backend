package dk.kino.service.hall;

import dk.kino.dto.HallDTO;
import dk.kino.dto.SeatDTO;
import dk.kino.entity.Hall;
import dk.kino.entity.Seat;
import dk.kino.entity.SeatPrice;
import dk.kino.exception.BadRequestException;
import dk.kino.repository.CinemaRepository;
import dk.kino.repository.HallRepository;
import dk.kino.service.SeatPriceService;
import dk.kino.service.SeatService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;
    private final SeatService seatService;
    private final SeatPriceService seatPriceService;

    public HallServiceImpl(HallRepository hallRepository, CinemaRepository cinemaRepository,SeatService seatService,SeatPriceService seatPriceService) {
        this.hallRepository = hallRepository;
        this.cinemaRepository = cinemaRepository;
        this.seatService = seatService;
        this.seatPriceService = seatPriceService;
    }

    @Override
    public List<HallDTO> findAll() {
        return hallRepository.findAllByIsActiveTrue().stream().map(this::convertHallToDTO).collect(Collectors.toList());
    }

    @Override
    public HallDTO findById(int id) {
        Optional<Hall> hall = hallRepository.findById(id);
        return hall.map(this::convertHallToDTO).orElse(null);
    }

    @Override
    public HallDTO findByNameAndCinemaName(String hallName, String cinemaName) {
        Optional<Hall> hall = hallRepository.findByNameAndCinemaNameAndIsActiveTrue(hallName,cinemaName);
        return hall.map(this::convertHallToDTO).orElse(null);
    }


    @Override
    public HallDTO createHall(HallDTO hallDTO) {
        Hall hall = convertToEntity(hallDTO);
        hall.setActive(true);
        cinemaRepository.findById(hallDTO.getCinemaId()).ifPresent(hall::setCinema);
        Hall savedHall = hallRepository.save(hall);
        // CREATE SEATS
        Set<Seat> seats = createSeats(savedHall);
        Set<SeatDTO> seatDTOs = seats.stream().map(seatService::toDto).collect(Collectors.toSet());
        seatService.createSeats(seatDTOs);
        return convertHallToDTO(savedHall);
    }

    @Override
    @Transactional
    public HallDTO updateHall(int id, HallDTO hallDTO) {
        Optional<Hall> existingHall = hallRepository.findById(id);
        if (existingHall.isPresent()){
            Hall hall = existingHall.get();

            // Check if hall gets resized
            boolean resizeCol = hall.getNoOfColumns() != hallDTO.getNoOfColumns();
            boolean resizeRow = hall.getNoOfRows() != hallDTO.getNoOfRows();
            if(hall.getNoOfRows()<1 || hall.getNoOfColumns()<1) throw new BadRequestException("rows and columns must be above 1");
            if (resizeCol || resizeRow) {
                hall.setNoOfColumns(hallDTO.getNoOfColumns());
                hall.setNoOfRows(hallDTO.getNoOfRows());
                // Delete old seats
                Set<SeatDTO> oldSeats = hall.getSeats().stream().map(seatService::toDto).collect(Collectors.toSet());
                seatService.deleteSeats(oldSeats);
                // Create new seats
                seatService.createSeats(createSeats(hall).stream().map(seatService::toDto).collect(Collectors.toSet()));
            }
            // Update the entity's fields:
            hall.setName(hallDTO.getName());
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
        Hall hall = hallRepository.findById(id).orElseThrow(() -> new RuntimeException("Hall not found"));
        hall.setActive(false);
        hallRepository.save(hall);
    }

    private Set<Seat> createSeats(Hall hall) {
        int noOfCols = hall.getNoOfColumns();
        int noOfRows = hall.getNoOfRows();
        Set<Seat> seats = new HashSet<>();
        int seatIndex = 0;

        List<SeatPrice> seatPrices = seatPriceService.findAllSeatPrices();

        SeatPrice economyPrice = seatPrices.stream()
                .filter(seatPrice -> "economy".equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);
        SeatPrice standardPrice = seatPrices.stream()
                .filter(seatPrice -> "standard".equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);
        SeatPrice vipPrice = seatPrices.stream()
                .filter(seatPrice -> "vip".equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);

        for (int row = 1; row <= noOfRows; row++) {
            SeatPrice seatPrice = economyPrice;
            if (row > 2) {
                seatPrice = standardPrice;
            }
            if (row ==  noOfRows) {
                seatPrice = vipPrice;
            }
            for (int col = 1; col <= noOfCols; col++) {
                seats.add(Seat.builder()
                        .seatIndex(seatIndex)
                        .seatPrice(seatPrice)
                        .hall(hall)
                        .build());
                ++seatIndex;
            }
        }
        return seats;
    }

    @Override
    public HallDTO convertHallToDTO(Hall hall) {
        HallDTO dto = new HallDTO();
        dto.setId(hall.getId());
        dto.setName(hall.getName());
        dto.setNoOfRows(hall.getNoOfRows());
        dto.setNoOfColumns(hall.getNoOfColumns());
        dto.setImageUrl(hall.getImageUrl());
        dto.setActive(hall.isActive());
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
        hall.setActive(hallDTO.isActive());
        hall.setCinema(cinemaRepository.findById(hallDTO.getCinemaId()).orElse(null));
        return hall;
    }

    @Override
    public List<HallDTO> findHallsByCinemaId(int cinemaId) {
        List<Hall> halls = hallRepository.findByCinemaIdAndIsActiveTrue(cinemaId);
        return halls.stream().map(this::convertHallToDTO).collect(Collectors.toList());
    }
}
