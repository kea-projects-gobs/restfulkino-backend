package dk.kino.configuration;

import dk.kino.entity.Cinema;
import dk.kino.repository.CinemaRepository;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupCinemas implements ApplicationRunner {

    private CinemaRepository cinemaRepository;

    public SetupCinemas(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCinemas();
    }

    private void createCinemas() {
        List<Cinema> cinemas = Arrays.asList(
            new Cinema("Cinema Paradiso", "Springfield", "123 Main St", "A lovely place to watch films.", "123-456-7890", "info@cinemaparadiso.com", "http://example.com/image1.png"),
            new Cinema("The Grand Screen", "Shelbyville", "456 Grand Ave", "Experience movies like never before.", "987-654-3210", "contact@thegrandscreen.com", "http://example.com/image2.png"),
            new Cinema("Movie Palace", "Capital City", "789 Royal Rd", "The ultimate cinema experience.", "555-123-4567", "hello@moviepalace.com", "http://example.com/image3.png")
        );

        cinemas.forEach(cinemaRepository::save);
    }

}
