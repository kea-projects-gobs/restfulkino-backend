package dk.kino.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO {
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    @JsonProperty("is3d")
    private boolean is3d;
    @JsonProperty("isLongMovie")
    private boolean isLongMovie;
    private int movieId;
    private int hallId;
}
