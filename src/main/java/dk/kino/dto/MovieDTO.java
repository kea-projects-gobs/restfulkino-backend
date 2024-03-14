package dk.kino.dto;


import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {
    private int id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private String imageUrl;
    private String language;
    private String genre;
    private String director;
    private String cast;
}
