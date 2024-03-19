package dk.kino.configuration;

import dk.kino.dto.CinemaDTO;
import dk.kino.dto.MovieDTO;
import dk.kino.dto.ScheduleDTO;
import dk.kino.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.kino.service.MovieService;
import dk.kino.service.ScheduleService;
import dk.kino.service.TicketService;
import dk.kino.service.cinema.CinemaService;
import dk.kino.service.hall.HallService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupData implements ApplicationRunner {

    private final HallService hallService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final ScheduleService scheduleService;
    private final TicketService ticketService;
    public SetupData(HallService hallService, CinemaService cinemaService, MovieService movieService,ScheduleService scheduleService,TicketService ticketService) {
        this.hallService = hallService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.ticketService = ticketService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initData();
    }


    private void initData() {
        // create Cinema
        List<Cinema> cinemas = Arrays.asList(
                new Cinema("Cinema Paradiso", "Springfield", "123 Main St", "A lovely place to watch films.", "123-456-7890", "info@cinemaparadiso.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
                new Cinema("The Grand Screen", "Shelbyville", "456 Grand Ave", "Experience movies like never before.", "987-654-3210", "contact@thegrandscreen.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg")
        );
        cinemas.forEach(cinema -> {
            CinemaDTO cinemaDTO = cinemaService.createCinema(cinemaService.convertToDTO(cinema));
            cinema.setId(cinemaDTO.getId());
        });

        // Create halls
        List<Hall> halls = new ArrayList<>();
        cinemas.forEach(cinema -> {
            halls.add(new Hall("Hall 1", 10, 10, "random.com", cinema));
            halls.add(new Hall("Hall 2", 10, 10, "random.com", cinema));
        });
        halls.forEach(hall -> hallService.createHall(hallService.convertHallToDTO(hall)));


        // Create Movies
        List<Movie> movies = Arrays.asList(
                new Movie("Kung Fu Panda 4", "The beloved Kung Fu Panda returns in an all-new adventure.", LocalDate.parse("2024-03-10"), 97, "https://image.tmdb.org/t/p/w300/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg", "English", "Comedy/Adventure", "John Stevenson", "Jack Black, Angelina Jolie"),
                new Movie("Dune: Part Two", "The saga of Dune continues as Paul Atreides fights to reclaim the desert planet.", LocalDate.parse("2024-10-20"), 155, "https://image.tmdb.org/t/p/w300/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg", "English", "Sci-fi/Adventure", "Denis Villeneuve", "Timothée Chalamet, Zendaya"),
                new Movie("Arthur the King", "A man and a dog embark on an extraordinary adventure.", LocalDate.parse("2024-07-12"), 118, "https://upload.wikimedia.org/wikipedia/en/9/94/Arthur_the_king_poster.jpg", "English", "Adventure/Action", "Simon Cellan Jones", "Mark Wahlberg, Simu Liu"),
                new Movie("Imaginary", "A journey into a child's imagination turns into a thrilling adventure.", LocalDate.parse("2024-05-06"), 102, "https://upload.wikimedia.org/wikipedia/en/7/77/Imaginary_2024_film_poster.jpeg", "English", "Horror/Childhood Homes", "N/A", "N/A"),
                new Movie("Cabrini", "A drama that delves into the lives of Italian immigrants.", LocalDate.parse("2024-08-15"), 130, "https://upload.wikimedia.org/wikipedia/en/a/a8/Cabrini_Official_Theatrical_Poster_%282024_film%29.jpg", "Italian", "Drama", "N/A", "N/A"),
                new Movie("Bob Marley: One Love", "A musical journey through the life of Bob Marley.", LocalDate.parse("2024-04-20"), 135, "https://upload.wikimedia.org/wikipedia/en/0/06/Bob_Marley_One_Love.jpg", "English", "Musical/Drama", "N/A", "N/A"),
                new Movie("Love Lies Bleeding", "A tale of romance entangled with crime.", LocalDate.parse("2024-09-09"), 123, "https://upload.wikimedia.org/wikipedia/en/d/dc/Love_lies_bleeding_poster.jpg", "English", "Romance/Crime", "N/A", "N/A"),
                new Movie("The American...", "A comedy about the absurdities of American life.", LocalDate.parse("2024-11-11"), 110, "https://example.com/theamerican.jpg", "English", "Comedy/Fantasy", "N/A", "N/A"),
                new Movie("Ordinary Angels", "A heartwarming drama set in 1994.", LocalDate.parse("2024-12-25"), 117, "https://upload.wikimedia.org/wikipedia/en/9/9e/Ordinary_angels_poster.png", "English", "Drama", "N/A", "N/A"),
                new Movie("One Life", "A gripping war drama that tells a story of survival and bravery.", LocalDate.parse("2024-06-06"), 142, "https://upload.wikimedia.org/wikipedia/en/a/a5/One_Life_poster.jpg", "English", "War/Drama", "N/A", "N/A"),
                new Movie("Knox Goes Away", "A thrilling narrative of a man on the run.", LocalDate.parse("2024-01-01"), 129, "https://upload.wikimedia.org/wikipedia/en/thumb/4/45/Knox_Goes_Away_film_poster.jpg/220px-Knox_Goes_Away_film_poster.jpg", "English", "Thriller", "N/A", "N/A"),
                new Movie("Madame Web", "A sci-fi adventure that explores new dimensions.", LocalDate.parse("2024-02-22"), 134, "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Madame_Web_%28film%29_poster.jpg/220px-Madame_Web_%28film%29_poster.jpg", "English", "Action/Sci-fi", "N/A", "N/A"),
                new Movie("Migration", "A comedic look at the adventures of migrating birds.", LocalDate.parse("2023-03-15"), 105, "https://upload.wikimedia.org/wikipedia/en/thumb/c/cb/Migration_%282023_film%29.jpg/220px-Migration_%282023_film%29.jpg", "English", "Adventure/Comedy", "N/A", "N/A"),
                new Movie("Oppenheimer", "A mystery thriller that delves into the mind of J. Robert Oppenheimer.", LocalDate.parse("2024-07-16"), 150, "https://image.tmdb.org/t/p/w300/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg", "English", "Thriller/Mystery", "Christopher Nolan", "Cillian Murphy"),
                new Movie("Snack Shack", "A drama that unfolds in a small town snack shack.", LocalDate.parse("2024-08-08"), 98, "https://upload.wikimedia.org/wikipedia/en/thumb/d/d5/Snack_Shack_poster.jpg/220px-Snack_Shack_poster.jpg", "English", "Drama/Comedy", "N/A", "N/A")
        );
        movies.forEach(movie -> {
            MovieDTO movieDTO = movieService.create(movieService.toDto(movie));
            movie.setId(movieDTO.getId());
        });

        // Create Schedule
        List<Schedule> schedules = Arrays.asList(
                Schedule.builder().date(LocalDate.parse("2024-03-20")).is3d(false).isLongMovie(false).startTime(LocalTime.parse("14:00:00")).movie(movies.get(0)).hall(halls.get(0)).build(),
                Schedule.builder().date(LocalDate.parse("2024-03-21")).is3d(false).isLongMovie(false).startTime(LocalTime.parse("14:00:00")).movie(movies.get(0)).hall(halls.get(0)).build(),
                Schedule.builder().date(LocalDate.parse("2024-03-22")).is3d(false).isLongMovie(false).startTime(LocalTime.parse("14:00:00")).movie(movies.get(0)).hall(halls.get(0)).build(),
                Schedule.builder().date(LocalDate.parse("2024-03-23")).is3d(false).isLongMovie(false).startTime(LocalTime.parse("14:00:00")).movie(movies.get(0)).hall(halls.get(0)).build()
        );
        schedules.forEach(schedule -> {
            ScheduleDTO scheduleDTO = scheduleService.create(scheduleService.toDto(schedule));
            schedule.setId(scheduleDTO.getId());
        });

        // Create Tickets
        List<Ticket> tickets = Arrays.asList(
                Ticket.builder().price(80.0).seat(Seat.builder().id(1).build()).build(),
                Ticket.builder().price(80.0).seat(Seat.builder().id(2).build()).build(),
                Ticket.builder().price(80.0).seat(Seat.builder().id(3).build()).build(),
                Ticket.builder().price(80.0).seat(Seat.builder().id(4).build()).build()
        );
        ticketService.createTickets(tickets.stream().map(ticketService::toDto).toList());

    }
}
