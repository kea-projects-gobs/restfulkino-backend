package dk.kino.service;

import dk.kino.dto.TicketDTO;
import dk.kino.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {
    Optional<TicketDTO> getTicketById(int id);
    TicketDTO createTicket(TicketDTO ticketDTO);
    List<TicketDTO> createTickets(List<TicketDTO> ticketDTOs);
    Ticket toEntity(TicketDTO ticketDTO);
    TicketDTO toDto(Ticket ticket);
}
