package dk.kino.service.impl;

import dk.kino.dto.*;
import dk.kino.entity.Reservation;
import dk.kino.entity.Schedule;
import dk.kino.entity.Seat;
import dk.kino.entity.Ticket;
import dk.kino.exception.BadRequestException;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.ReservationRepository;
import dk.kino.service.ReservationService;
import dk.kino.service.ScheduleService;
import dk.kino.service.SeatService;
import dk.kino.service.TicketService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final ScheduleService scheduleService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,TicketService ticketService,SeatService seatService,ScheduleService scheduleService) {
        this.reservationRepository = reservationRepository;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.scheduleService = scheduleService;
    }

    @Override
    @Transactional
    public ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO) {
        // USE PRICE SERVICE
        double PRICE_LONG_MOVIE = 20.0;
        double PRICE_3D = 10.0;
        double FEE = 0.07;
        double DISCOUNT = 0.05;

        double TICKET_PRICE_ADJUSTMENT = 0;

        Reservation reservation = toEntity(reservationReqDTO);
        ScheduleDTO scheduleDTO = scheduleService.findById(reservation.getSchedule().getId()).orElseThrow(() -> new NotFoundException("Schedule not found"));

        if(scheduleDTO.isLongMovie()) TICKET_PRICE_ADJUSTMENT+=PRICE_LONG_MOVIE;
        if(scheduleDTO.is3d()) TICKET_PRICE_ADJUSTMENT+=PRICE_3D;

        List<Integer> seatIdsReserved = getReservedSeatIdsByScheduleId(scheduleDTO.getId());

        double subTotal = 0;
        // Create tickets
        for (Ticket ticket : reservation.getTickets()) {
            int seatId = ticket.getSeat().getId();

            if(seatIdsReserved.contains(seatId)) throw new BadRequestException("Seat is already taken");

            // SET SEAT
            SeatDTO seatDTO = seatService.findSeatById(seatId).orElseThrow(() -> new NotFoundException("Seat not found"));

            // Check if seat belongs to hall
            if(seatDTO.getHallId() != scheduleDTO.getHallId()) throw new BadRequestException("Seat does not belong to Hall");
            
            ticket.setSeat(seatService.toEntity(seatDTO));
            // SET PRICE
            double ticketPrice = seatDTO.getCurrentPrice()+TICKET_PRICE_ADJUSTMENT;
            ticket.setPrice(ticketPrice);
            subTotal+=ticketPrice;
        }

        if(reservation.getTickets().size() < 6) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(subTotal*FEE));
        if(reservation.getTickets().size() > 10) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(-subTotal*DISCOUNT));

        Reservation savedReservation = reservationRepository.save(reservation);
        for (Ticket ticket : reservation.getTickets()) {
            ticket.setReservation(savedReservation);
        }
        ticketService.createTickets(reservation.getTickets().stream().map(ticketService::toDto).collect(Collectors.toList()));

        return toDto(savedReservation);
    }

    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private List<Integer> getReservedSeatIdsByScheduleId(int scheduleId) {
        // Fetch reservations for the given scheduleId
        List<Reservation> reservations = reservationRepository.findAllByScheduleId(scheduleId);

        // Extract seat IDs from reservations
        return reservations.stream()
                .flatMap(reservation -> reservation.getTickets().stream())
                .map(ticket -> ticket.getSeat().getId())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReservationResDTO> findReservationById(int id) {
        return reservationRepository.findById(id).map(this::toDto);
    }

    @Override
    public ReservationResDTO toDto(Reservation reservation) {
        return ReservationResDTO.builder()
                .id(reservation.getId())
                .reservationDate(reservation.getReservationDate())
                .scheduleId(reservation.getSchedule().getId())
                .feeOrDiscount(reservation.getFeeOrDiscount())
                .tickets(reservation.getTickets().stream().map(ticketService::toDto).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Reservation toEntity(ReservationReqDTO reservationReqDTO) {
        // Create tickets from seatIds
        Set<Seat> seats = reservationReqDTO.getSeatIds().stream().map(id -> Seat.builder().id(id).build()).collect(Collectors.toSet());
        Set<Ticket> tickets = seats.stream().map(seat -> Ticket.builder().seat(seat).build()).collect(Collectors.toSet());
        return Reservation.builder()
                .id(reservationReqDTO.getId())
                .feeOrDiscount(reservationReqDTO.getFeeOrDiscount())
                .reservationDate(reservationReqDTO.getReservationDate())
                .schedule(Schedule.builder().id(reservationReqDTO.getScheduleId()).build())
                .tickets(tickets)
                .build();
    }
}
