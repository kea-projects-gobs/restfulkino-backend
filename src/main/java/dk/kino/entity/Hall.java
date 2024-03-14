package dk.kino.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "hall")
    private List<Schedule> schedules;

    public Hall(String name, int noOfRows, int noOfColumns, String imageUrl, Cinema cinema) {
        this.name = name;
        this.noOfRows = noOfRows;
        this.noOfColumns = noOfColumns;
        this.imageUrl = imageUrl;
        this.cinema = cinema;
    }
}
