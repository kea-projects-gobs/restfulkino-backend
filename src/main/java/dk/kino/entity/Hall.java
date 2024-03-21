package dk.kino.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;
    private int noOfRows;
    private int noOfColumns;
    private String imageUrl;
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private List<Schedule> schedules;
    @OneToMany(mappedBy = "hall")
    private Set<Seat> seats;

    public Hall(String name, int noOfRows, int noOfColumns, String imageUrl, Cinema cinema) {
        this.name = name;
        this.noOfRows = noOfRows;
        this.noOfColumns = noOfColumns;
        this.imageUrl = imageUrl;
        this.cinema = cinema;
    }
}
