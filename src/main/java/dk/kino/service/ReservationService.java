package dk.kino.service;

import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.entity.Reservation;

import java.util.Optional;

public interface ReservationService {
    ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO);
    Optional<ReservationResDTO> findReservationById(int id);
    ReservationResDTO toDto(Reservation reservation);
    Reservation toEntity(ReservationReqDTO reservationReqDTO);
}
