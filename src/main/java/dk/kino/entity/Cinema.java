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
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,length = 50,unique = true)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    private String description;
    private String phone;
    private String email;
    private String imageUrl;
    private boolean isActive;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hall> halls;

    public Cinema(String name, String city, String street, String description, String phone, String email, String imageUrl) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.description = description;
        this.phone = phone;
        this.email = email;
        this.imageUrl = imageUrl;
    }
}
