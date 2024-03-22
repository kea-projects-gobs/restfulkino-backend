package dk.kino.api;

import dk.kino.entity.MoviePrice;
import dk.kino.entity.ReservationPrice;
import dk.kino.entity.SeatPrice;
import dk.kino.exception.NotFoundException;
import dk.kino.service.MoviePriceService;
import dk.kino.service.ReservationPriceService;
import dk.kino.service.SeatPriceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final SeatPriceService seatPriceService;
    private final MoviePriceService moviePriceService;
    private final ReservationPriceService reservationPriceService;

    public PriceController(SeatPriceService seatPriceService, MoviePriceService moviePriceService, ReservationPriceService reservationPriceService) {
        this.seatPriceService = seatPriceService;
        this.moviePriceService = moviePriceService;
        this.reservationPriceService = reservationPriceService;
    }


    @Operation(summary = "Get all seat prices", description = "Returns a list of all seat prices.")
    @GetMapping("/seats")
    public ResponseEntity<List<SeatPrice>> getAllSeatPrices() {
        return ResponseEntity.ok().body(seatPriceService.findAllSeatPrices());
    }

    @Operation(summary = "Get seat price by name", description = "Returns a single seat price, if one with the given name exists.")
    @GetMapping("/seats/{name}")
    public ResponseEntity<SeatPrice> getSeatPriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(seatPriceService.findSeatPriceByName(name).orElseThrow(() -> new NotFoundException("Seat price not found")));
    }

    @Operation(summary = "Update a seat price", description = "Updates a seat price.")
    @PutMapping("/seats/{name}")
    public ResponseEntity<SeatPrice> updateSeatPrice(@PathVariable String name, @RequestBody SeatPrice seatPrice) {
        return ResponseEntity.ok().body(seatPriceService.updateSeatPrice(name,seatPrice));
    }

    @Operation(summary = "Get all reservation prices", description = "Returns a list of all reservation prices.")
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationPrice>> getAllReservationPrices() {
        return ResponseEntity.ok().body(reservationPriceService.findAllReservationPrices());
    }

    @Operation(summary = "Get reservation price by name", description = "Returns a single reservation price, if one with the given name exists.")
    @GetMapping("/reservations/{name}")
    public ResponseEntity<ReservationPrice> getReservationPriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(reservationPriceService.findReservationPriceByName(name).orElseThrow(() -> new NotFoundException("Reservation price not found")));
    }

    @Operation(summary = "Update a reservation price", description = "Updates a reservation price.")
    @PutMapping("/reservations/{name}")
    public ResponseEntity<ReservationPrice> updateReservationPrice(@PathVariable String name, @RequestBody ReservationPrice reservationPrice) {
        return ResponseEntity.ok().body(reservationPriceService.updateReservationPrice(name,reservationPrice));
    }

    @Operation(summary = "Get all movie prices", description = "Returns a list of all movie prices.")
    @GetMapping("/movies")
    public ResponseEntity<List<MoviePrice>> getAllMoviePrices() {
        return ResponseEntity.ok().body(moviePriceService.findAllMoviePrices());
    }

    @Operation(summary = "Get movie price by name", description = "Returns a single movie price, if one with the given name exists.")
    @GetMapping("/movies/{name}")
    public ResponseEntity<MoviePrice> getMoviePriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(moviePriceService.findMoviePriceByName(name).orElseThrow(() -> new NotFoundException("Movie price not found")));
    }

    @Operation(summary = "Update a movie price", description = "Updates a movie price.")
    @PutMapping("/movies/{name}")
    public ResponseEntity<MoviePrice> updateMoviePrice(@PathVariable String name, @RequestBody MoviePrice moviePrice) {
        return ResponseEntity.ok().body(moviePriceService.updateMoviePrice(name,moviePrice));
    }
}
