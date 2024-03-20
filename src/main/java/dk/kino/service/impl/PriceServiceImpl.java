package dk.kino.service.impl;

import dk.kino.entity.MoviePrice;
import dk.kino.entity.ReservationPrice;
import dk.kino.entity.SeatPrice;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.MoviePriceRepository;
import dk.kino.repository.ReservationPriceRepository;
import dk.kino.repository.SeatPriceRepository;
import dk.kino.service.MoviePriceService;
import dk.kino.service.ReservationPriceService;
import dk.kino.service.SeatPriceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriceServiceImpl implements SeatPriceService, MoviePriceService, ReservationPriceService {
    private final SeatPriceRepository seatPriceRepository;
    private final MoviePriceRepository moviePriceRepository;
    private final ReservationPriceRepository reservationPriceRepository;

    public PriceServiceImpl(SeatPriceRepository seatPriceRepository,MoviePriceRepository moviePriceRepository,ReservationPriceRepository reservationPriceRepository) {
        this.seatPriceRepository = seatPriceRepository;
        this.moviePriceRepository = moviePriceRepository;
        this.reservationPriceRepository = reservationPriceRepository;
    }

    @Override
    public Optional<SeatPrice> findSeatPriceByName(String name) {
        return seatPriceRepository.findById(name);
    }

    @Override
    public List<SeatPrice> findAllSeatPrices() {
        return seatPriceRepository.findAll();
    }

    @Override
    public SeatPrice createSeatPrice(SeatPrice seatPrice) {
        return seatPriceRepository.save(seatPrice);
    }

    @Override
    public SeatPrice updateSeatPrice(String name, SeatPrice seatPrice) {
        SeatPrice existingSeatPrice = seatPriceRepository.findById(seatPrice.getName()).orElseThrow(() -> new NotFoundException("Could not find seat price name"));
        existingSeatPrice.setUnit(seatPrice.getUnit());
        existingSeatPrice.setAmount(seatPrice.getAmount());
        return seatPriceRepository.save(existingSeatPrice);
    }

    @Override
    public Optional<MoviePrice> findMoviePriceByName(String name) {
        return moviePriceRepository.findById(name);
    }

    @Override
    public List<MoviePrice> findAllMoviePrices() {
        return moviePriceRepository.findAll();
    }

    @Override
    public MoviePrice createMoviePrice(MoviePrice moviePrice) {
        return moviePriceRepository.save(moviePrice);
    }

    @Override
    public MoviePrice updateMoviePrice(String name, MoviePrice moviePrice) {
        MoviePrice existingMoviePrice = moviePriceRepository.findById(moviePrice.getName()).orElseThrow(() -> new NotFoundException("Could not find seat price name"));
        existingMoviePrice.setUnit(moviePrice.getUnit());
        existingMoviePrice.setAmount(moviePrice.getAmount());
        return moviePriceRepository.save(existingMoviePrice);
    }

    @Override
    public Optional<ReservationPrice> findReservationPriceByName(String name) {
        return reservationPriceRepository.findById(name);
    }

    @Override
    public List<ReservationPrice> findAllReservationPrices() {
        return reservationPriceRepository.findAll();
    }

    @Override
    public ReservationPrice createReservationPrice(ReservationPrice reservationPrice) {
        return reservationPriceRepository.save(reservationPrice);
    }

    @Override
    public ReservationPrice updateReservationPrice(String name, ReservationPrice reservationPrice) {
        ReservationPrice existingReservationPrice = reservationPriceRepository.findById(reservationPrice.getName()).orElseThrow(() -> new NotFoundException("Could not find seat price name"));
        existingReservationPrice.setUnit(reservationPrice.getUnit());
        existingReservationPrice.setAmount(reservationPrice.getAmount());
        return reservationPriceRepository.save(existingReservationPrice);
    }
}
