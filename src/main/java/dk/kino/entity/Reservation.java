package dk.kino.entity;

import dk.security.entity.UserWithRoles;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;
    @OneToMany(mappedBy = "reservation")
    private Set<Ticket> tickets;
    @Temporal(TemporalType.DATE)
    private LocalDate reservationDate;
    private double feeOrDiscount; // Fee (positive) or discount (negative) applied to the reservation
    @ManyToOne
    private UserWithRoles user;
}
