package dk.kino.configuration;

import dk.kino.entity.Cinema;
import dk.kino.entity.Hall;
import dk.kino.entity.Movie;
import dk.kino.repository.CinemaRepository;
import dk.kino.repository.HallRepository;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import dk.kino.repository.MovieRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupCinemasAndHalls implements ApplicationRunner {

    private CinemaRepository cinemaRepository;
    private HallRepository hallRepository;
    private MovieRepository movieRepository;

    public SetupCinemasAndHalls(CinemaRepository cinemaRepository, HallRepository hallRepository, MovieRepository movieRepository) {
        this.cinemaRepository = cinemaRepository;
        this.hallRepository = hallRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCinemas();
        createHalls();
        createMovies();
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

    private void createMovies(){
        List<Movie> movies = Arrays.asList(
            new Movie("Kung Fu Panda 4", "The beloved Kung Fu Panda returns in an all-new adventure.", "2024-03-10", 97, "https://image.tmdb.org/t/p/w300/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg", "English", "Comedy/Adventure", "John Stevenson", "Jack Black, Angelina Jolie"),
            new Movie("Dune: Part Two", "The saga of Dune continues as Paul Atreides fights to reclaim the desert planet.", "2024-10-20", 155, "https://image.tmdb.org/t/p/w300/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg", "English", "Sci-fi/Adventure", "Denis Villeneuve", "Timothée Chalamet, Zendaya"),
            new Movie("Arthur the King", "A man and a dog embark on an extraordinary adventure.", "2024-07-12", 118, "https://upload.wikimedia.org/wikipedia/en/9/94/Arthur_the_king_poster.jpg", "English", "Adventure/Action", "Simon Cellan Jones", "Mark Wahlberg, Simu Liu"),
            new Movie("Imaginary", "A journey into a child's imagination turns into a thrilling adventure.", "2024-05-06", 102, "https://upload.wikimedia.org/wikipedia/en/7/77/Imaginary_2024_film_poster.jpeg", "English", "Horror/Childhood Homes", "N/A", "N/A"),
            new Movie("Cabrini", "A drama that delves into the lives of Italian immigrants.", "2024-08-15", 130, "https://upload.wikimedia.org/wikipedia/en/a/a8/Cabrini_Official_Theatrical_Poster_%282024_film%29.jpg", "Italian", "Drama", "N/A", "N/A"),
            new Movie("Bob Marley: One Love", "A musical journey through the life of Bob Marley.", "2024-04-20", 135, "https://upload.wikimedia.org/wikipedia/en/0/06/Bob_Marley_One_Love.jpg", "English", "Musical/Drama", "N/A", "N/A"),
            new Movie("Love Lies Bleeding", "A tale of romance entangled with crime.", "2024-09-09", 123, "https://upload.wikimedia.org/wikipedia/en/d/dc/Love_lies_bleeding_poster.jpg", "English", "Romance/Crime", "N/A", "N/A"),
            new Movie("The American...", "A comedy about the absurdities of American life.", "2024-11-11", 110, "https://example.com/theamerican.jpg", "English", "Comedy/Fantasy", "N/A", "N/A"),
            new Movie("Ordinary Angels", "A heartwarming drama set in 1994.", "2024-12-25", 117, "https://upload.wikimedia.org/wikipedia/en/9/9e/Ordinary_angels_poster.png", "English", "Drama", "N/A", "N/A"),
            new Movie("One Life", "A gripping war drama that tells a story of survival and bravery.", "2024-06-06", 142, "https://upload.wikimedia.org/wikipedia/en/a/a5/One_Life_poster.jpg", "English", "War/Drama", "N/A", "N/A"),
            new Movie("Knox Goes Away", "A thrilling narrative of a man on the run.", "2024-01-01", 129, "https://upload.wikimedia.org/wikipedia/en/thumb/4/45/Knox_Goes_Away_film_poster.jpg/220px-Knox_Goes_Away_film_poster.jpg", "English", "Thriller", "N/A", "N/A"),
            new Movie("Madame Web", "A sci-fi adventure that explores new dimensions.", "2024-02-22", 134, "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Madame_Web_%28film%29_poster.jpg/220px-Madame_Web_%28film%29_poster.jpg", "English", "Action/Sci-fi", "N/A", "N/A"),
            new Movie("Migration", "A comedic look at the adventures of migrating birds.", "2023-03-15", 105, "https://upload.wikimedia.org/wikipedia/en/thumb/c/cb/Migration_%282023_film%29.jpg/220px-Migration_%282023_film%29.jpg", "English", "Adventure/Comedy", "N/A", "N/A"),
            new Movie("Oppenheimer", "A mystery thriller that delves into the mind of J. Robert Oppenheimer.", "2024-07-16", 150, "https://image.tmdb.org/t/p/w300/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg", "English", "Thriller/Mystery", "Christopher Nolan", "Cillian Murphy"),
            new Movie("Snack Shack", "A drama that unfolds in a small town snack shack.", "2024-08-08", 98, "https://upload.wikimedia.org/wikipedia/en/thumb/d/d5/Snack_Shack_poster.jpg/220px-Snack_Shack_poster.jpg", "English", "Drama/Comedy", "N/A", "N/A")
        );
        movies.forEach(movieRepository::save);
    }

}
