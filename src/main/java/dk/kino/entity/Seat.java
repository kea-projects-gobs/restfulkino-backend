package dk.kino.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int seatIndex;
    private double currentPrice;
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hall;
    private boolean isActive;
}
