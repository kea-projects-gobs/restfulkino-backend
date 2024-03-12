package dk.kino.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Getter
@Setter
@NoArgsConstructor
public class MovieDTO {
    private int id;
    private String title;
    private String description;
    private String releaseDate;
    private int duration;
    private String imageUrl;
    private String language;
    private String genre;
    private String director;
    private String cast;
}
