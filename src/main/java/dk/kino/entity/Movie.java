package dk.kino.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    private String releaseDate;

    private int duration;

    private String imageUrl;

    private String language;

    @Column(nullable = false)
    private String genre;

    private String director;

    private String cast;


    public Movie(String title, String description, String releaseDate, int duration, String imageUrl, String language, String genre, String director, String cast) {
        this.title = title;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.language = language;
        this.genre = genre;
        this.director = director;
        this.cast = cast;
    }
}