package dk.kino.service.impl;

import dk.kino.dto.SeatDTO;
import dk.kino.entity.Hall;
import dk.kino.entity.Seat;
import dk.kino.entity.SeatPrice;
import dk.kino.repository.SeatRepository;
import dk.kino.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public Optional<SeatDTO> findSeatById(int id) {
        return seatRepository.findById(id).map(this::toDto);
    }

    @Override
    public List<SeatDTO> findSeatsByHallId(int hallId) {
        return seatRepository.findByHallIdAndIsActiveTrue(hallId).stream().map(this::toDto).toList();
    }

    @Override
    public SeatDTO createSeat(SeatDTO seatDTO) {
        Seat seat = toEntity(seatDTO);
        seat.setActive(true);
        return toDto(seatRepository.save(seat));
    }

    @Override
    public List<SeatDTO> createSeats(Set<SeatDTO> seatDTOs) {
        Set<Seat> seats = seatDTOs.stream().map(this::toEntity).collect(Collectors.toSet());
        seats.forEach(seat -> seat.setActive(true));
        return seatRepository.saveAll(seats).stream().map(this::toDto).toList();
    }

    @Override
    public void deleteSeats(Set<SeatDTO> seatDTOs) {
        Set<Seat> seats = seatDTOs.stream().map(this::toEntity).collect(Collectors.toSet());
        // Soft deletion
        seats.forEach(seat -> seat.setActive(false));
        seatRepository.saveAll(seats);
    }

    @Override
    public SeatDTO toDto(Seat entity) {
        return SeatDTO.builder()
                .id(entity.getId())
                .seatIndex(entity.getSeatIndex())
                .hallId(entity.getHall().getId())
                .seatPriceId(entity.getSeatPrice().getName())
                .build();
    }

    @Override
    public Seat toEntity(SeatDTO dto) {
        Hall hall = new Hall();
        hall.setId(dto.getHallId());
        return Seat.builder()
                .id(dto.getId())
                .seatIndex(dto.getSeatIndex())
                .seatPrice(SeatPrice.builder().name(dto.getSeatPriceId()).build())
                .hall(hall)
                .build();
    }
}
