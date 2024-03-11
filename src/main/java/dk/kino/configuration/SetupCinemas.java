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
            new Cinema("Cinema Paradiso", "Springfield", "123 Main St", "A lovely place to watch films.", "123-456-7890", "info@cinemaparadiso.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
            new Cinema("The Grand Screen", "Shelbyville", "456 Grand Ave", "Experience movies like never before.", "987-654-3210", "contact@thegrandscreen.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
            new Cinema("Movie Palace", "Capital City", "789 Royal Rd", "The ultimate cinema experience.", "555-123-4567", "hello@moviepalace.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg")
        );

        cinemas.forEach(cinemaRepository::save);
    }

}
