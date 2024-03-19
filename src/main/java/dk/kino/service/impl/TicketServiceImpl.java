package dk.kino.service.impl;

import dk.kino.dto.TicketDTO;
import dk.kino.entity.Reservation;
import dk.kino.entity.Seat;
import dk.kino.entity.Ticket;
import dk.kino.repository.TicketRepository;
import dk.kino.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Optional<TicketDTO> getTicketById(int id) {
        return ticketRepository.findById(id).map(this::toDto);
    }

    @Override
    public TicketDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = toEntity(ticketDTO);
        return toDto(ticketRepository.save(ticket));
    }

    @Override
    public List<TicketDTO> createTickets(List<TicketDTO> ticketDTOs) {
        List<Ticket> tickets = ticketDTOs.stream().map(this::toEntity).toList();
        return ticketRepository.saveAll(tickets).stream().map(this::toDto).toList();
    }

    @Override
    public Ticket toEntity(TicketDTO ticketDTO) {
        return Ticket.builder()
                .price(ticketDTO.getPrice())
                .seat(Seat.builder().id(ticketDTO.getSeatId()).build())
                .price(ticketDTO.getPrice())
                .reservation(Reservation.builder().id(ticketDTO.getReservationId()).build())
                .build();
    }

    @Override
    public TicketDTO toDto(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .seatId(ticket.getSeat().getId())
                .price(ticket.getPrice())
                .reservationId(ticket.getReservation().getId())
                .build();
    }
}
