package dk.kino.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class ReservationReqDTO {
    private int scheduleId;
    private Set<Integer> seatIndexes;
}
