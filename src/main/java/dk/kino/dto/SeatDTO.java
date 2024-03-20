package dk.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SeatDTO {
    private int id;
    private int seatIndex;
    private String seatPriceId;
    private int hallId;
}
