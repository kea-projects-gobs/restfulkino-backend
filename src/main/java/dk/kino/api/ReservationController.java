package dk.kino.api;

import dk.kino.dto.ReservationPriceCalcDTO;
import dk.kino.dto.ReservationReqDTO;
import dk.kino.dto.ReservationResDTO;
import dk.kino.dto.SeatDTO;
import dk.kino.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Create a reservation", description = "Creates a new reservation.")
    @PostMapping
    public ResponseEntity<ReservationResDTO> createReservation(@RequestBody ReservationReqDTO reservationReqDTO, Principal principal){
        return ResponseEntity.ok().body(reservationService.createReservation(reservationReqDTO,principal));
    }

    @Operation(summary = "Calculate price", description = "Calculates the price for a reservation based on the provided reservation request.")
    @PostMapping("/prices")
    public ResponseEntity<ReservationPriceCalcDTO> calculatePrice(@RequestBody ReservationReqDTO reservationReqDTO){
        return ResponseEntity.ok().body(reservationService.calculatePrice(reservationReqDTO));
    }

    @Operation(summary = "Get reservation by schedule id", description = "Returns a list of reservations for a schedule with the given id.")
    @GetMapping("/schedules/{id}/seats")
    public ResponseEntity<List<SeatDTO>> findAllReservedSeatsByScheduleId(@PathVariable int id) {
        return ResponseEntity.ok().body(reservationService.findAllReservedSeatsByScheduleId(id));
    }
}
