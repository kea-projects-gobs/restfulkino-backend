package dk.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class ReservationReqDTO {
    private int id;
    private int scheduleId;
    private Set<Integer> seatIds;
    private LocalDate reservationDate;
    private double feeOrDiscount;
}
