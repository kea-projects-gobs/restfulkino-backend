package dk.kino.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CinemaDTO {
    private int id;
    private String name;
    private String city;
    private String street;
    private String description;
    private String phone;
    private String email;
    private String imageUrl;
    private boolean isActive;
    private List<HallDTO> halls;
}
