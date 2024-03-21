package dk.kino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatPrice {
    @Id
    private String name;
    private double amount;
    private String unit;
}
