package dk.kino.api;

import dk.kino.entity.MoviePrice;
import dk.kino.entity.ReservationPrice;
import dk.kino.entity.SeatPrice;
import dk.kino.exception.NotFoundException;
import dk.kino.service.MoviePriceService;
import dk.kino.service.ReservationPriceService;
import dk.kino.service.SeatPriceService;
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

    @GetMapping("/seats")
    public ResponseEntity<List<SeatPrice>> getAllSeatPrices() {
        return ResponseEntity.ok().body(seatPriceService.findAllSeatPrices());
    }

    @GetMapping("/seats/{name}")
    public ResponseEntity<SeatPrice> getSeatPriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(seatPriceService.findSeatPriceByName(name).orElseThrow(() -> new NotFoundException("Seat price not found")));
    }

    @PutMapping("/seats/{name}")
    public ResponseEntity<SeatPrice> updateSeatPrice(@PathVariable String name, @RequestBody SeatPrice seatPrice) {
        return ResponseEntity.ok().body(seatPriceService.updateSeatPrice(name,seatPrice));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationPrice>> getAllReservationPrices() {
        return ResponseEntity.ok().body(reservationPriceService.findAllReservationPrices());
    }

    @GetMapping("/reservations/{name}")
    public ResponseEntity<ReservationPrice> getReservationPriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(reservationPriceService.findReservationPriceByName(name).orElseThrow(() -> new NotFoundException("Reservation price not found")));
    }

    @PutMapping("/reservations/{name}")
    public ResponseEntity<ReservationPrice> updateReservationPrice(@PathVariable String name, @RequestBody ReservationPrice reservationPrice) {
        return ResponseEntity.ok().body(reservationPriceService.updateReservationPrice(name,reservationPrice));
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MoviePrice>> getAllMoviePrices() {
        return ResponseEntity.ok().body(moviePriceService.findAllMoviePrices());
    }

    @GetMapping("/movies/{name}")
    public ResponseEntity<MoviePrice> getMoviePriceByName(@PathVariable String name) {
        return ResponseEntity.ok().body(moviePriceService.findMoviePriceByName(name).orElseThrow(() -> new NotFoundException("Movie price not found")));
    }

    @PutMapping("/movies/{name}")
    public ResponseEntity<MoviePrice> updateMoviePrice(@PathVariable String name, @RequestBody MoviePrice moviePrice) {
        return ResponseEntity.ok().body(moviePriceService.updateMoviePrice(name,moviePrice));
    }
}
