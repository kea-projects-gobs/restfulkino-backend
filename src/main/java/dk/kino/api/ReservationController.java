package dk.kino.api;

import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
