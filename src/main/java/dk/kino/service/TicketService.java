package dk.kino.service;

import dk.kino.dto.TicketDTO;
import dk.kino.entity.Ticket;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing a service for managing tickets.
 */
public interface TicketService {

    /**
     * Retrieves a ticket by its unique identifier.
     *
     * @param id The unique identifier of the ticket.
     * @return An optional containing the DTO representing the found ticket, or empty if not found.
     */
    Optional<TicketDTO> getTicketById(int id);

    /**
     * Creates a new ticket.
     *
     * @param ticketDTO The DTO containing details for the ticket to be created.
     * @return The DTO representing the created ticket.
     */
    TicketDTO createTicket(TicketDTO ticketDTO);

    /**
     * Creates multiple tickets.
     *
     * @param ticketDTOs The list of DTOs containing details for the tickets to be created.
     * @return A list of DTOs representing the created tickets.
     */
    List<TicketDTO> createTickets(List<TicketDTO> ticketDTOs);

    /**
     * Converts a ticket DTO to its corresponding entity representation.
     *
     * @param ticketDTO The DTO to convert.
     * @return The entity representing the ticket.
     */
    Ticket toEntity(TicketDTO ticketDTO);

    /**
     * Converts a ticket entity to its corresponding DTO representation.
     *
     * @param ticket The entity to convert.
     * @return The DTO representing the ticket.
     */
    TicketDTO toDto(Ticket ticket);
}

