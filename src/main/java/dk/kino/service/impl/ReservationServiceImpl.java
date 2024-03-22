package dk.kino.service.impl;

import dk.kino.dto.*;
import dk.kino.entity.*;
import dk.kino.exception.BadRequestException;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.ReservationRepository;
import dk.kino.service.*;
import dk.kino.service.HallService;
import dk.security.entity.UserWithRoles;
import dk.security.service.UserWithRolesService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;
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
    private final HallService hallService;
    private final SeatPriceService seatPriceService;
    private final MoviePriceService moviePriceService;
    private final ReservationPriceService reservationPriceService;
    private final UserWithRolesService userWithRolesService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,TicketService ticketService,SeatService seatService,
                                  ScheduleService scheduleService,HallService hallService,SeatPriceService seatPriceService,
                                  MoviePriceService moviePriceService,ReservationPriceService reservationPriceService,
                                  UserWithRolesService userWithRolesService) {
        this.reservationRepository = reservationRepository;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.scheduleService = scheduleService;
        this.hallService = hallService;
        this.seatPriceService = seatPriceService;
        this.moviePriceService = moviePriceService;
        this.reservationPriceService = reservationPriceService;
        this.userWithRolesService = userWithRolesService;
    }

    private SeatPrice findSeatPriceByNameFromList(List<SeatPrice> seatPrices, String name) {
        return seatPrices.stream()
                .filter(seatPrice -> name.equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Price not found"));
    }

    private double getTicketPriceAdjustment(ScheduleDTO scheduleDTO) {
        double ticketPriceAdjustment = 0;
        double priceLongMovie = moviePriceService.findMoviePriceByName("longMovie").orElseThrow(() -> new NotFoundException("Price not found")).getAmount();
        double price3D = moviePriceService.findMoviePriceByName("threeD").orElseThrow(() -> new NotFoundException("Price not found")).getAmount();
        // Adjust prices
        if(scheduleDTO.isLongMovie()) ticketPriceAdjustment+=priceLongMovie;
        if(scheduleDTO.is3d()) ticketPriceAdjustment+=price3D;
        return ticketPriceAdjustment;
    }

    private List<SeatDTO> getHallSeats(ScheduleDTO scheduleDTO) {
        HallDTO hallDTO = hallService.findByNameAndCinemaName(scheduleDTO.getHallName(),scheduleDTO.getCinemaName());
        return seatService.findSeatsByHallId(hallDTO.getId());
    }

    private Reservation reservationBuilder(ReservationReqDTO reservationReqDTO) {
        // FEE OR DISCOUNT SETTINGS
        int NUMBER_OF_TICKETS_FOR_FEE = 6;
        int NUMBER_OF_TICKETS_FOR_DISCOUNT = 11;

        // Convert to entity
        Reservation reservation = toEntity(reservationReqDTO);

        // Get prices
        List<SeatPrice> seatPrices = seatPriceService.findAllSeatPrices();
        double feeFraction = reservationPriceService.findReservationPriceByName("fee").orElseThrow(() -> new NotFoundException("Price not found")).getAmount();
        double discountFraction = reservationPriceService.findReservationPriceByName("discount").orElseThrow(() -> new NotFoundException("Price not found")).getAmount();

        // Get Schedule
        ScheduleDTO scheduleDTO = scheduleService.findById(reservation.getSchedule().getId()).orElseThrow(() -> new NotFoundException("Schedule not found"));

        // Get price adjustments for ticket
        double ticketPriceAdjustment = getTicketPriceAdjustment(scheduleDTO);

        // Get seats
        List<SeatDTO> seatDTOs = getHallSeats(scheduleDTO);
        List<Integer> seatIdsReserved = getReservedSeatIdsByScheduleId(scheduleDTO.getId());

        // Keep track of total for adding fees or discount
        double subTotal = 0;

        // Create tickets
        for (Ticket ticket : reservation.getTickets()) {

            // Get seat id from seatIndex
            int seatId = seatDTOs.stream()
                    .filter(seat -> seat.getSeatIndex() == ticket.getSeat().getSeatIndex())
                    .map(SeatDTO::getId)
                    .findFirst().orElseThrow(() -> new NotFoundException("Seat is not found"));

//            // Set seat id on ticket
//            ticket.getSeat().setId(seatId);

            // Validate seat is free
            if(seatIdsReserved.contains(seatId)) throw new BadRequestException("Seat is already taken");

            // SET SEAT
            SeatDTO seatDTO = seatService.findSeatById(seatId).orElseThrow(() -> new NotFoundException("Seat not found"));
            ticket.setSeat(seatService.toEntity(seatDTO));

            double seatPrice = findSeatPriceByNameFromList(seatPrices,seatDTO.getSeatPriceName()).getAmount();

            // SET PRICE
            double ticketPrice = seatPrice+ticketPriceAdjustment;
            ticket.setPrice(ticketPrice);
            subTotal+=ticketPrice;
        }

        // SET FEE OR DISCOUNT
        if(reservation.getTickets().size() < NUMBER_OF_TICKETS_FOR_FEE) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(subTotal*feeFraction));
        if(reservation.getTickets().size() >= NUMBER_OF_TICKETS_FOR_DISCOUNT) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(-subTotal*discountFraction));

        // ADD RESERVATION DATE
        reservation.setReservationDate(LocalDate.now());

        return reservation;
    }

    @Override
    public ReservationPriceCalcDTO calculatePrice(ReservationReqDTO reservationReqDTO) {
        Reservation reservation = reservationBuilder(reservationReqDTO);

        for (Ticket ticket : reservation.getTickets()) {
            ticket.setReservation(reservation);
        }

        return toReservationPriceCalcDTO(reservation);
    }

    @Override
    @Transactional
    public ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO, Principal principal) {
        Reservation reservation = reservationBuilder(reservationReqDTO);

        // ADD user
        String currentUserName = principal.getName();
        userWithRolesService.getUserWithRoles(currentUserName);
        UserWithRoles userWithRoles = new UserWithRoles();
        userWithRoles.setUsername(currentUserName);
        reservation.setUser(userWithRoles);

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
        List<Reservation> reservations = reservationRepository.findAllByScheduleId(scheduleId);

        // Extract seat ids
        return reservations.stream()
                .flatMap(reservation -> reservation.getTickets().stream())
                .map(ticket -> ticket.getSeat().getId()).toList();
    }

    @Override
    public List<SeatDTO> findAllReservedSeatsByScheduleId(int scheduleId) {
        List<Reservation> reservations = reservationRepository.findAllByScheduleId(scheduleId);
        return reservations.stream()
                .flatMap(reservation -> reservation.getTickets().stream())
                .map(Ticket::getSeat).toList().stream().map(seatService::toDto).toList();
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
        Set<Seat> seats = reservationReqDTO.getSeatIndexes().stream().map(index -> Seat.builder().seatIndex(index).build()).collect(Collectors.toSet());
        Set<Ticket> tickets = seats.stream().map(seat -> Ticket.builder().seat(seat).build()).collect(Collectors.toSet());
        return Reservation.builder()
                .schedule(Schedule.builder().id(reservationReqDTO.getScheduleId()).build())
                .tickets(tickets)
                .build();
    }

    private ReservationPriceCalcDTO toReservationPriceCalcDTO(Reservation reservation) {
        return ReservationPriceCalcDTO.builder()
                .scheduleId(reservation.getId())
                .feeOrDiscount(reservation.getFeeOrDiscount())
                .tickets(reservation.getTickets().stream().map(this::toTicketPriceCalcDTO).collect(Collectors.toSet()))
                .build();
    }

    private TicketPriceCalcDTO toTicketPriceCalcDTO(Ticket ticket) {
        return TicketPriceCalcDTO.builder()
                .seatId(ticket.getSeat().getId())
                .seatIndex(ticket.getSeat().getSeatIndex())
                .price(ticket.getPrice())
                .build();
    }
}
