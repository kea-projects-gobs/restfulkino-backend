package dk.kino.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalTime startTime;
    private LocalDate date;
    private boolean is3d;
    private boolean isHelaften;
    // TODO: Add when Movie entity is created
//    @ManyToOne
//    @JoinColumn(name = "movie_id", nullable = false)
//    private Movie movie;

    // TODO: Add when Hall entity is created
//    @ManyToOne
//    @JoinColumn(name = "hall_id", nullable = false)
//    private Hall hall;
}
