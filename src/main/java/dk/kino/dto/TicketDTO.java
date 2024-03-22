package dk.kino.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TicketDTO {
    private int id;
    private int seatId;
    private double price;
    private int reservationId;
}
