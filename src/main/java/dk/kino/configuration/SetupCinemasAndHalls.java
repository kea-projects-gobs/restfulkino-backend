package dk.kino.configuration;

import dk.kino.entity.Cinema;
import dk.kino.entity.Hall;
import dk.kino.repository.CinemaRepository;
import dk.kino.repository.HallRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupCinemasAndHalls implements ApplicationRunner {

    private CinemaRepository cinemaRepository;
    private HallRepository hallRepository;

    public SetupCinemasAndHalls(CinemaRepository cinemaRepository, HallRepository hallRepository) {
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCinemas();
        createHalls();
    }

    private void createCinemas() {
        List<Cinema> cinemas = Arrays.asList(
            new Cinema("Cinema Paradiso", "Springfield", "123 Main St", "A lovely place to watch films.", "123-456-7890", "info@cinemaparadiso.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
            new Cinema("The Grand Screen", "Shelbyville", "456 Grand Ave", "Experience movies like never before.", "987-654-3210", "contact@thegrandscreen.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
            new Cinema("Movie Palace", "Capital City", "789 Royal Rd", "The ultimate cinema experience.", "555-123-4567", "hello@moviepalace.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
            new Cinema("Flicks Family Cinema", "Oakwood", "101 Oak St", "Family fun and entertainment.", "111-222-3333", "info@flicksfamily.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Retro Cinema", "Pineville", "202 Pine Ave", "Back to the classics.", "222-333-4444", "contact@retrocinema.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Indie Films Corner", "Mapleton", "303 Maple Dr", "Independent films from around the world.", "333-444-5555", "hello@indiefilms.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Action Arena", "Rivertown", "404 River Rd", "High-octane action movies.", "444-555-6666", "action@arena.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Horror House", "Lakewood", "505 Lake Ln", "Scary stories all night long.", "555-666-7777", "screams@horrorhouse.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Sci-Fi Sphere", "Starfield", "606 Galaxy Way", "Explore new worlds.", "666-777-8888", "discover@scifisphere.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Romantic Rendezvous", "Lovetown", "707 Heart St", "Love stories that touch your heart.", "777-888-9999", "romance@rendezvous.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Comedy Club Cinema", "Laughlin", "808 Joke Rd", "Laughter is the best medicine.", "888-999-0000", "funny@comedyclubcinema.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Documentary Den", "Factville", "909 Truth Tr", "Real stories, real people.", "999-000-1111", "reality@documentaryden.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg"),
            new Cinema("Animation Station", "Toon Town", "1010 Animation Ave", "Animated adventures for all ages.", "000-111-2222", "animation@station.com", "https://batterseapowerstation.co.uk/content/uploads/2022/08/Cinema-in-the-Power-Station-image001hero-1600x869.jpg")
        );

        cinemas.forEach(cinemaRepository::save);
        // Ensure cinemas are persisted and available in DB before creating halls
        cinemaRepository.flush();
    }

        private void createHalls() {
        List<Hall> halls = Arrays.asList(
            new Hall("Hall 1", 10, 10, "random.com", cinemaRepository.findById(2).orElseThrow(() -> new NoSuchElementException("Cinema with ID 2 not found"))),
            new Hall("Hall 2", 10, 10, "random.com", cinemaRepository.findById(2).orElseThrow(() -> new NoSuchElementException("Cinema with ID 2 not found"))),
            new Hall("Hall 3", 10, 10, "random.com", cinemaRepository.findById(3).orElseThrow(() -> new NoSuchElementException("Cinema with ID 3 not found"))),
            new Hall("Hall 4", 10, 10, "random.com", cinemaRepository.findById(4).orElseThrow(() -> new NoSuchElementException("Cinema with ID 4 not found")))
        );
        hallRepository.saveAll(halls);
    }

}
