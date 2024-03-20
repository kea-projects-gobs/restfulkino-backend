package dk.kino.api;

import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.dto.SeatDTO;
import dk.kino.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationResDTO> createReservation(@RequestBody ReservationReqDTO reservationReqDTO){
        return ResponseEntity.ok().body(reservationService.createReservation(reservationReqDTO));
    }

    @PostMapping("/prices")
    public ResponseEntity<ReservationResDTO> calculatePrice(@RequestBody ReservationReqDTO reservationReqDTO){
        return ResponseEntity.ok().body(reservationService.calculatePrice(reservationReqDTO));
    }

    @GetMapping("/schedules/{id}/seats")
    public ResponseEntity<List<SeatDTO>> findAllReservedSeatsByScheduleId(@PathVariable int id) {
        return ResponseEntity.ok().body(reservationService.findAllReservedSeatsByScheduleId(id));
    }
}
