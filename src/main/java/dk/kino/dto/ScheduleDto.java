package dk.kino.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDto {
    private Integer id;
    private LocalTime startTime;
    private LocalDate date;
    private boolean is3d;
    private boolean isHelaften;
    private String movieTitle;
    private String hallName;
    private String cinemaName;
}
