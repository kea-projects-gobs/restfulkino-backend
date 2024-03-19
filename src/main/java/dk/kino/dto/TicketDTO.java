package dk.kino.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@Builder
public class TicketDTO {
    private int id;
    private int seatId;
    private double price;
}
