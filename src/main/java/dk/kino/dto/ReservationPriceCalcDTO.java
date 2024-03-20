package dk.kino.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class ReservationPriceCalcDTO {
    private int scheduleId;
    private Set<TicketPriceCalcDTO> tickets;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "#.##")
    private double feeOrDiscount;
}
