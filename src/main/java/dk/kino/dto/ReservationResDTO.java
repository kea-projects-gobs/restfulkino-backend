package dk.kino.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class ReservationResDTO {
    private int id;
    private int scheduleId;
    private Set<TicketDTO> tickets;
    private LocalDate reservationDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT, pattern = "#.##")
    private double feeOrDiscount;
}
