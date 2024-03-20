package dk.kino.service.impl;

import dk.kino.dto.*;
import dk.kino.entity.*;
import dk.kino.exception.BadRequestException;
import dk.kino.exception.NotFoundException;
import dk.kino.repository.ReservationRepository;
import dk.kino.service.*;
import dk.kino.service.hall.HallService;
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

    private List<MoviePrice> getMoviePrices() {
        return moviePriceService.findAllMoviePrices();
    }

    private List<ReservationPrice> getReservationPrices() {
        return reservationPriceService.findAllReservationPrices();
    }

    private List<SeatPrice> getSeatPrices() {
        return seatPriceService.findAllSeatPrices();
    }

    private MoviePrice findMoviePriceByNameFromList(List<MoviePrice> moviePrices, String name) {
        return moviePrices.stream()
                .filter(seatPrice -> name.equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);
    }

    private ReservationPrice findReservationPriceByNameFromList(List<ReservationPrice> reservationPrices, String name) {
        return reservationPrices.stream()
                .filter(seatPrice -> name.equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);
    }

    private SeatPrice findSeatPriceByNameFromList(List<SeatPrice> seatPrices, String name) {
        return seatPrices.stream()
                .filter(seatPrice -> name.equalsIgnoreCase(seatPrice.getName()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ReservationResDTO calculatePrice(ReservationReqDTO reservationReqDTO) {
        // GET PRICES
        List<SeatPrice> seatPrices = getSeatPrices();
        List<MoviePrice> moviePrices = getMoviePrices();
        List<ReservationPrice> reservationPrices = getReservationPrices();

        double PRICE_LONG_MOVIE = findMoviePriceByNameFromList(moviePrices,"longMovie").getAmount();
        double PRICE_3D = findMoviePriceByNameFromList(moviePrices,"threeD").getAmount();
        double FEE = findReservationPriceByNameFromList(reservationPrices,"fee").getAmount();
        double DISCOUNT = findReservationPriceByNameFromList(reservationPrices,"discount").getAmount();


        double TICKET_PRICE_ADJUSTMENT = 0;

        Reservation reservation = toEntity(reservationReqDTO);
        ScheduleDTO scheduleDTO = scheduleService.findById(reservation.getSchedule().getId()).orElseThrow(() -> new NotFoundException("Schedule not found"));
        HallDTO hallDTO = hallService.findByNameAndCinemaName(scheduleDTO.getHallName(),scheduleDTO.getCinemaName());
        List<SeatDTO> seatDTOs = seatService.findSeatsByHallId(hallDTO.getId());

        // Adjust prices
        if(scheduleDTO.isLongMovie()) TICKET_PRICE_ADJUSTMENT+=PRICE_LONG_MOVIE;
        if(scheduleDTO.is3d()) TICKET_PRICE_ADJUSTMENT+=PRICE_3D;

        // Get reserved seats
        List<Integer> seatIdsReserved = getReservedSeatIdsByScheduleId(scheduleDTO.getId());

        double subTotal = 0;

        // Create tickets
        for (Ticket ticket : reservation.getTickets()) {

            // Get seat id from seatIndex
            int seatId = seatDTOs.stream()
                    .filter(seat -> seat.getSeatIndex() == ticket.getSeat().getSeatIndex())
                    .map(SeatDTO::getId)
                    .findFirst().orElseThrow(() -> new NotFoundException("Seat is not found"));

            // Set seat id on ticket
            ticket.getSeat().setId(seatId);

            if(seatIdsReserved.contains(seatId)) throw new BadRequestException("Seat is already taken");

            // SET SEAT
            SeatDTO seatDTO = seatService.findSeatById(seatId).orElseThrow(() -> new NotFoundException("Seat not found"));

            ticket.setSeat(seatService.toEntity(seatDTO));

            double seatPrice = findSeatPriceByNameFromList(seatPrices,seatDTO.getSeatPriceName()).getAmount();

            // SET PRICE
            double ticketPrice = seatPrice+TICKET_PRICE_ADJUSTMENT;
            ticket.setPrice(ticketPrice);
            subTotal+=ticketPrice;
        }

        if(reservation.getTickets().size() < 6) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(subTotal*FEE));
        if(reservation.getTickets().size() > 10) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(-subTotal*DISCOUNT));

        reservation.setReservationDate(LocalDate.now());

//        Reservation savedReservation = reservationRepository.save(reservation);
        for (Ticket ticket : reservation.getTickets()) {
            ticket.setReservation(reservation);
        }
//        ticketService.createTickets(reservation.getTickets().stream().map(ticketService::toDto).collect(Collectors.toList()));

        return toDto(reservation);
    }

    @Override
    @Transactional
    public ReservationResDTO createReservation(ReservationReqDTO reservationReqDTO, Principal principal) {
        // GET PRICES
        List<SeatPrice> seatPrices = getSeatPrices();
        List<MoviePrice> moviePrices = getMoviePrices();
        List<ReservationPrice> reservationPrices = getReservationPrices();

        double PRICE_LONG_MOVIE = findMoviePriceByNameFromList(moviePrices,"longMovie").getAmount();
        double PRICE_3D = findMoviePriceByNameFromList(moviePrices,"threeD").getAmount();
        double FEE = findReservationPriceByNameFromList(reservationPrices,"fee").getAmount();
        double DISCOUNT = findReservationPriceByNameFromList(reservationPrices,"discount").getAmount();

        double TICKET_PRICE_ADJUSTMENT = 0;

        Reservation reservation = toEntity(reservationReqDTO);
        ScheduleDTO scheduleDTO = scheduleService.findById(reservation.getSchedule().getId()).orElseThrow(() -> new NotFoundException("Schedule not found"));
        HallDTO hallDTO = hallService.findByNameAndCinemaName(scheduleDTO.getHallName(),scheduleDTO.getCinemaName());
        List<SeatDTO> seatDTOs = seatService.findSeatsByHallId(hallDTO.getId());

        // Adjust prices
        if(scheduleDTO.isLongMovie()) TICKET_PRICE_ADJUSTMENT+=PRICE_LONG_MOVIE;
        if(scheduleDTO.is3d()) TICKET_PRICE_ADJUSTMENT+=PRICE_3D;

        // Get reserved seats
        List<Integer> seatIdsReserved = getReservedSeatIdsByScheduleId(scheduleDTO.getId());

        double subTotal = 0;

        // Create tickets
        for (Ticket ticket : reservation.getTickets()) {

            // Get seat id from seatIndex
            int seatId = seatDTOs.stream()
                    .filter(seat -> seat.getSeatIndex() == ticket.getSeat().getSeatIndex())
                    .map(SeatDTO::getId)
                    .findFirst().orElseThrow(() -> new NotFoundException("Seat is not found"));

            // Set seat id on ticket
            ticket.getSeat().setId(seatId);

            if(seatIdsReserved.contains(seatId)) throw new BadRequestException("Seat is already taken");

            // SET SEAT
            SeatDTO seatDTO = seatService.findSeatById(seatId).orElseThrow(() -> new NotFoundException("Seat not found"));

            ticket.setSeat(seatService.toEntity(seatDTO));

            double seatPrice = findSeatPriceByNameFromList(seatPrices,seatDTO.getSeatPriceName()).getAmount();

            // SET PRICE
            double ticketPrice = seatPrice+TICKET_PRICE_ADJUSTMENT;
            ticket.setPrice(ticketPrice);
            subTotal+=ticketPrice;
        }

        if(reservation.getTickets().size() < 6) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(subTotal*FEE));
        if(reservation.getTickets().size() > 10) reservation.setFeeOrDiscount(roundToTwoDecimalPlaces(-subTotal*DISCOUNT));

        reservation.setReservationDate(LocalDate.now());

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
}
