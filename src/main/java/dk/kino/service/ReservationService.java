package dk.kino.service;

import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.dto.SeatDTO;
import dk.kino.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {
    ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO);
    Optional<ReservationResDTO> findReservationById(int id);
    List<SeatDTO> findAllReservedSeatsByScheduleId(int scheduleId);
    ReservationResDTO toDto(Reservation reservation);
    Reservation toEntity(ReservationReqDTO reservationReqDTO);
}
