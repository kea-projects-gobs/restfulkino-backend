package dk.kino.service;

import dk.kino.dto.SeatDTO;
import dk.kino.entity.Seat;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SeatService {
    Optional<SeatDTO> findSeatById(int id);
    List<SeatDTO> findSeatsByHallId(int hallId);
    SeatDTO createSeat(SeatDTO seatDTO);
    List<SeatDTO> createSeats(Set<SeatDTO> seatDTOs);
    void deleteSeats(Set<SeatDTO> seatDTOs);
    SeatDTO toDto(Seat entity);
    Seat toEntity(SeatDTO dto);
}
