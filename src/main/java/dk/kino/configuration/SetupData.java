package dk.kino.configuration;

import dk.kino.dto.*;
import dk.kino.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import dk.kino.service.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SetupData implements ApplicationRunner {

    private final HallService hallService;
    private final CinemaService cinemaService;
    private final MovieService movieService;
    private final ScheduleService scheduleService;
    private final ReservationService reservationService;
    private final SeatPriceService seatPriceService;
    private final MoviePriceService moviePriceService;
    private final ReservationPriceService reservationPriceService;
    public SetupData(HallService hallService, CinemaService cinemaService, MovieService movieService,ScheduleService scheduleService,
                     ReservationService reservationService,SeatPriceService seatPriceService,
                     MoviePriceService moviePriceService,ReservationPriceService reservationPriceService) {
        this.hallService = hallService;
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.scheduleService = scheduleService;
        this.reservationService = reservationService;
        this.seatPriceService = seatPriceService;
        this.moviePriceService = moviePriceService;
        this.reservationPriceService = reservationPriceService;
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

        // CREATE PRICES
        List<SeatPrice> seatPrices = Arrays.asList(
                new SeatPrice("economy",80,"DKK"),
                new SeatPrice("standard",100,"DKK"),
                new SeatPrice("vip",120,"DKK")
        );
        seatPrices.forEach(seatPriceService::createSeatPrice);

        List<MoviePrice> moviePrices = Arrays.asList(
                new MoviePrice("longMovie",25,"DKK"),
                new MoviePrice("threeD",20,"DKK")
        );
        moviePrices.forEach(moviePriceService::createMoviePrice);

        List<ReservationPrice> reservationPrices = Arrays.asList(
                new ReservationPrice("fee",0.07,"percentInDecimal"),
                new ReservationPrice("discount",0.05,"percentInDecimal")
        );
        reservationPrices.forEach(reservationPriceService::createReservationPrice);

        // Create halls
        List<Hall> halls = new ArrayList<>();
        cinemas.forEach(cinema -> {
            halls.add(new Hall("Hall 1", 10, 10, "random.com", cinema));
            halls.add(new Hall("Hall 2", 10, 10, "random.com", cinema));
        });
        halls.forEach(hall -> {
            HallDTO hallDTO = hallService.createHall(hallService.convertHallToDTO(hall));
            hall.setId(hallDTO.getId());
        });


        // Create Movies
        List<Movie> movies = Arrays.asList(
                new Movie("Kung Fu Panda 4", "The beloved Kung Fu Panda returns in an all-new adventure.", LocalDate.parse("2024-03-10"), 97, "https://image.tmdb.org/t/p/w300/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg", "English", "Comedy/Adventure", "John Stevenson", "Jack Black, Angelina Jolie"),
                new Movie("Dune: Part Two", "The saga of Dune continues as Paul Atreides fights to reclaim the desert planet.", LocalDate.parse("2024-02-27"), 155, "https://image.tmdb.org/t/p/w300/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg", "English", "Sci-fi/Adventure", "Denis Villeneuve", "Timothée Chalamet, Zendaya"),
                new Movie("Arthur the King", "A man and a dog embark on an extraordinary adventure.", LocalDate.parse("2024-03-15"), 118, "https://upload.wikimedia.org/wikipedia/en/9/94/Arthur_the_king_poster.jpg", "English", "Adventure/Action", "Simon Cellan Jones", "Mark Wahlberg, Simu Liu"),
                new Movie("Imaginary", "A journey into a child's imagination turns into a thrilling adventure.", LocalDate.parse("2024-12-21"), 102, "https://upload.wikimedia.org/wikipedia/en/7/77/Imaginary_2024_film_poster.jpeg", "English", "Horror/Childhood Homes", "N/A", "N/A"),
                new Movie("Cabrini", "A drama that delves into the lives of Italian immigrants.", LocalDate.parse("2024-03-08"), 130, "https://upload.wikimedia.org/wikipedia/en/a/a8/Cabrini_Official_Theatrical_Poster_%282024_film%29.jpg", "Italian", "Drama", "N/A", "N/A"),
                new Movie("Bob Marley: One Love", "A musical journey through the life of Bob Marley.", LocalDate.parse("2024-02-14"), 135, "https://upload.wikimedia.org/wikipedia/en/0/06/Bob_Marley_One_Love.jpg", "English", "Musical/Drama", "N/A", "N/A"),
                new Movie("Love Lies Bleeding", "A tale of romance entangled with crime.", LocalDate.parse("2024-03-08"), 123, "https://upload.wikimedia.org/wikipedia/en/d/dc/Love_lies_bleeding_poster.jpg", "English", "Romance/Crime", "N/A", "N/A"),
                new Movie("Godzilla x Kong: The New Empire", "Two ancient titans, Godzilla and Kong, clash in an epic battle as humans unravel their intertwined origins and connection to Skull Island's mysteries.", LocalDate.parse("2024-03-27"), 115, "https://upload.wikimedia.org/wikipedia/en/b/be/Godzilla_x_kong_the_new_empire_poster.jpg", "English", "Action", "N/A", "N/A"),
                new Movie("Ordinary Angels", "A heartwarming drama set in 1994.", LocalDate.parse("2024-12-25"), 117, "https://upload.wikimedia.org/wikipedia/en/9/9e/Ordinary_angels_poster.png", "English", "Drama", "N/A", "N/A"),
                new Movie("One Life", "A gripping war drama that tells a story of survival and bravery.", LocalDate.parse("2024-02-23"), 142, "https://upload.wikimedia.org/wikipedia/en/a/a5/One_Life_poster.jpg", "English", "War/Drama", "N/A", "N/A"),
                new Movie("Knox Goes Away", "A thrilling narrative of a man on the run.", LocalDate.parse("2024-01-01"), 129, "https://upload.wikimedia.org/wikipedia/en/thumb/4/45/Knox_Goes_Away_film_poster.jpg/220px-Knox_Goes_Away_film_poster.jpg", "English", "Thriller", "N/A", "N/A"),
                new Movie("Madame Web", "A sci-fi adventure that explores new dimensions.", LocalDate.parse("2024-02-22"), 134, "https://upload.wikimedia.org/wikipedia/en/thumb/f/f0/Madame_Web_%28film%29_poster.jpg/220px-Madame_Web_%28film%29_poster.jpg", "English", "Action/Sci-fi", "N/A", "N/A"),
                new Movie("Migration", "A comedic look at the adventures of migrating birds.", LocalDate.parse("2023-03-15"), 105, "https://upload.wikimedia.org/wikipedia/en/thumb/c/cb/Migration_%282023_film%29.jpg/220px-Migration_%282023_film%29.jpg", "English", "Adventure/Comedy", "N/A", "N/A"),
                new Movie("Oppenheimer", "A mystery thriller that delves into the mind of J. Robert Oppenheimer.", LocalDate.parse("2023-07-21"), 150, "https://image.tmdb.org/t/p/w300/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg", "English", "Thriller/Mystery", "Christopher Nolan", "Cillian Murphy"),
                new Movie("Snack Shack", "A drama that unfolds in a small town snack shack.", LocalDate.parse("2024-03-15"), 98, "https://upload.wikimedia.org/wikipedia/en/thumb/d/d5/Snack_Shack_poster.jpg/220px-Snack_Shack_poster.jpg", "English", "Drama/Comedy", "N/A", "N/A")
        );
        movies.forEach(movie -> {
            MovieDTO movieDTO = movieService.create(movieService.toDto(movie));
            movie.setId(movieDTO.getId());
        });

        // Screening times
        List<LocalTime> screeningTimes = Arrays.asList(
                LocalTime.parse("10:00:00"),
                LocalTime.parse("14:00:00"),
                LocalTime.parse("18:00:00"),
                LocalTime.parse("22:00:00")
        );

        List<Schedule> schedules = new ArrayList<>();

        // Filter movies that have release date not later than now
        List<Movie> filteredMovies = movies.stream()
            .filter(movie -> !movie.getReleaseDate().isAfter(LocalDate.now()))
            .collect(Collectors.toList());

            LocalDate today = LocalDate.now();
            int movieIndex = 0; // Start with the first movie
            for (int dayOffset = 0; dayOffset < 5; dayOffset++) {
                LocalDate scheduleDate = today.plusDays(dayOffset);
            
                for (Cinema cinema : cinemas) {
                    List<Hall> cinemaHalls = halls.stream()
                            .filter(hall -> hall.getCinema().getId() == cinema.getId())
                            .collect(Collectors.toList());
            
                    // Rotate through movies for each hall
                    for (Hall hall : cinemaHalls) {
                        // Select a movie based on the current index
                        Movie movie = filteredMovies.get(movieIndex % filteredMovies.size());
                        boolean isLongMovie = movie.getDuration() >= 170;
            
                        // Select a time slot for this movie (for simplicity, just one time slot per movie per day)
                        LocalTime time = screeningTimes.get(dayOffset % screeningTimes.size());
            
                        try {
                            Schedule schedule = Schedule.builder()
                                    .date(scheduleDate)
                                    .startTime(time)
                                    .movie(movie)
                                    .hall(hall)
                                    .is3d(false)
                                    .isLongMovie(isLongMovie)
                                    .build();
            
                            ScheduleDTO scheduleDTO = scheduleService.create(scheduleService.toDto(schedule));
                            schedule.setId(scheduleDTO.getId());
                            schedules.add(schedule);
                        } catch (RuntimeException e) {
                            System.out.println("Could not create schedule for movie " + movie.getTitle() + " at " + time.format(DateTimeFormatter.ofPattern("HH:mm")) + " in hall " + hall.getName() + " on " + scheduleDate + ": " + e.getMessage());
                        }
            
                        // Move to the next movie for the next hall
                        movieIndex++;
                    }
                }
            }

                    // Reservations
        Set<Integer> seatIds1 = new HashSet<>();
        Set<Integer> seatIds2 = new HashSet<>();
        Set<Integer> seatIds3 = new HashSet<>();
        for (int i=0; i<=10; i++) {
            if (i<=3) seatIds1.add(i);
            if (i>3 && i<=6) seatIds2.add(i);
            if (i>6) seatIds3.add(i);
        }

        List<ReservationReqDTO> reservations = Arrays.asList(
                ReservationReqDTO.builder().seatIndexes(seatIds1).scheduleId(schedules.get(0).getId()).build(),
                ReservationReqDTO.builder().seatIndexes(seatIds2).scheduleId(schedules.get(0).getId()).build(),
                ReservationReqDTO.builder().seatIndexes(seatIds3).scheduleId(schedules.get(0).getId()).build()
        );
//        reservations.forEach(reservationService::createReservation);
    }
}
