package dk.kino.service;

import static org.mockito.Mockito.*;

import dk.kino.dto.TicketDTO;
import dk.kino.entity.Reservation;
import dk.kino.entity.Seat;
import dk.kino.entity.Ticket;
import dk.kino.repository.TicketRepository;
import dk.kino.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    public void testGetTicketById_ExistingId_ReturnsTicketDTO() {
        // Arrange
        int id = 1;
        Ticket ticket = Ticket.builder().id(id).price(10.0).seat(Seat.builder().id(1).build()).reservation(Reservation.builder().id(1).build()).build();
        TicketDTO expectedTicketDTO = TicketDTO.builder().id(id).price(10.0).seatId(1).reservationId(1).build();

        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));

        // Act
        Optional<TicketDTO> result = ticketService.getTicketById(id);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expectedTicketDTO.getId(), result.get().getId());
        Assertions.assertEquals(expectedTicketDTO.getPrice(), result.get().getPrice());
        Assertions.assertEquals(expectedTicketDTO.getSeatId(), result.get().getSeatId());
    }

    @Test
    public void testGetTicketById_NonexistentId_ReturnsEmptyOptional() {
        // Arrange
        int id = 1;
        when(ticketRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<TicketDTO> result = ticketService.getTicketById(id);

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testCreateTicket_ValidTicketDTO_CreatesAndReturnsTicketDTO() {
        // Arrange
        TicketDTO ticketDTO = TicketDTO.builder().price(10.0).seatId(1).reservationId(1).build();
        Ticket savedTicket = Ticket.builder().id(1).price(10.0).seat(Seat.builder().id(1).build()).reservation(Reservation.builder().id(1).build()).build();
        TicketDTO expectedTicketDTO = TicketDTO.builder().id(1).price(10.0).seatId(1).build();

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        // Act
        TicketDTO result = ticketService.createTicket(ticketDTO);

        // Assert
        Assertions.assertEquals(expectedTicketDTO.getId(), result.getId());
        Assertions.assertEquals(expectedTicketDTO.getPrice(), result.getPrice());
        Assertions.assertEquals(expectedTicketDTO.getSeatId(), result.getSeatId());
    }

    @Test
    public void testCreateTickets_ValidTicketDTOs_CreatesAndReturnsTicketDTOs() {
        // Arrange
        List<TicketDTO> ticketDTOs = new ArrayList<>();
        ticketDTOs.add(TicketDTO.builder().price(10.0).seatId(1).reservationId(1).build());
        ticketDTOs.add(TicketDTO.builder().price(12.0).seatId(2).reservationId(1).build());

        List<Ticket> savedTickets = new ArrayList<>();
        savedTickets.add(Ticket.builder().id(1).price(10.0).seat(Seat.builder().id(1).build()).reservation(Reservation.builder().id(1).build()).build());
        savedTickets.add(Ticket.builder().id(2).price(12.0).seat(Seat.builder().id(2).build()).reservation(Reservation.builder().id(1).build()).build());

        List<TicketDTO> expectedTicketDTOs = new ArrayList<>();
        expectedTicketDTOs.add(TicketDTO.builder().id(1).price(10.0).seatId(1).build());
        expectedTicketDTOs.add(TicketDTO.builder().id(2).price(12.0).seatId(2).build());

        when(ticketRepository.saveAll(anyList())).thenReturn(savedTickets);

        // Act
        List<TicketDTO> result = ticketService.createTickets(ticketDTOs);

        // Assert
        Assertions.assertEquals(expectedTicketDTOs.get(0).getId(), result.get(0).getId());
        Assertions.assertEquals(expectedTicketDTOs.get(0).getPrice(), result.get(0).getPrice());
        Assertions.assertEquals(expectedTicketDTOs.get(0).getSeatId(), result.get(0).getSeatId());
        Assertions.assertEquals(expectedTicketDTOs.get(1).getId(), result.get(1).getId());
        Assertions.assertEquals(expectedTicketDTOs.get(1).getPrice(), result.get(1).getPrice());
        Assertions.assertEquals(expectedTicketDTOs.get(1).getSeatId(), result.get(1).getSeatId());
    }
}
