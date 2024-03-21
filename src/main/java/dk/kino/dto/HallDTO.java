package dk.kino.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HallDTO {
    private int id;
    private String name;
    private int noOfRows;
    private int noOfColumns;
    private String imageUrl;
    private int cinemaId;
    private boolean isActive;
}
