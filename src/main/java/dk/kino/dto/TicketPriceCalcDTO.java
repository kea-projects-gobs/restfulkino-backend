package dk.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TicketPriceCalcDTO {
    private int seatId;
    private int seatIndex;
    private double price;
}