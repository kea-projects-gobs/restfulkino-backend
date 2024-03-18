package dk.kino.service;

import dk.kino.dto.SeatDTO;
import dk.kino.entity.Seat;

import java.util.List;
import java.util.Set;

public interface SeatService {
    SeatDTO createSeat(SeatDTO seatDTO);
    List<SeatDTO> createSeats(Set<SeatDTO> seatDTOs);
    void deleteSeats(Set<SeatDTO> seatDTOs);
    SeatDTO toDto(Seat entity);
    Seat toEntity(SeatDTO dto);
}
