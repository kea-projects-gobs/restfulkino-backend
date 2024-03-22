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
    public void run(ApplicationArguments args) {
        initData();
    }


    private void initData() {
        // create Cinema
        List<Cinema> cinemas = Arrays.asList(
                new Cinema("Cinema Paradiso", "Springfield", "123 Main St", "Danmark's bedste biografoplevelse", "123-456-7890", "info@cinemaparadiso.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg"),
                new Cinema("The Grand Screen", "Shelbyville", "456 Grand Ave", "Danmark's næstbedste biografoplevelse", "987-654-3210", "contact@thegrandscreen.com", "https://cms-assets.webediamovies.pro/cdn-cgi/image/dpr=1,fit=scale-down,gravity=auto,metadata=none,quality=85,width=1920,height=1131/production/2/519760e8e151087beb072aa43b20abd0.jpeg")
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
        int cinemaCounter = 1;
        for (Cinema cinema : cinemas) {
            int numberOfHalls = (cinemaCounter == 1) ? 2 : 4; // Cinema 1 gets 2 halls, Cinema 2 gets 4 halls
            for (int i = 1; i <= numberOfHalls; i++) {
                halls.add(new Hall("Hall " + i, 10 + (i * 2), 10 + (i * 2), "random.com", cinema));
            }
            cinemaCounter++;
        }
        halls.forEach(hall -> {
            HallDTO hallDTO = hallService.createHall(hallService.convertHallToDTO(hall));
            hall.setId(hallDTO.getId());
        });


        // Create Movies
        List<Movie> movies = Arrays.asList(
                new Movie("Kung Fu Panda 4", "The beloved Kung Fu Panda returns in an all-new adventure.", LocalDate.parse("2024-03-10"), 97, "https://image.tmdb.org/t/p/w300/kDp1vUBnMpe8ak4rjgl3cLELqjU.jpg", "English", "Comedy/Adventure", "John Stevenson", "Jack Black, Angelina Jolie"),
                new Movie("Dune: Part Two", "The saga of Dune continues as Paul Atreides fights to reclaim the desert planet.", LocalDate.parse("2024-02-27"), 155, "https://image.tmdb.org/t/p/w300/8b8R8l88Qje9dn9OE8PY05Nxl1X.jpg", "English", "Sci-fi/Adventure", "Denis Villeneuve", "Timothée Chalamet, Zendaya"),
                new Movie("Arthur the King", "A man and a dog embark on an extraordinary adventure.", LocalDate.parse("2024-03-15"), 118, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTuL7XHL9-drgFKk9b3Tz7bZ3Bsv0F5vZNkYX-TnQ2AqO26Lfoo", "English", "Adventure/Action", "Simon Cellan Jones", "Mark Wahlberg, Simu Liu"),
                new Movie("Imaginary", "A journey into a child's imagination turns into a thrilling adventure.", LocalDate.parse("2024-12-21"), 102, "https://upload.wikimedia.org/wikipedia/en/7/77/Imaginary_2024_film_poster.jpeg", "English", "Horror/Childhood Homes", "N/A", "N/A"),
                new Movie("Cabrini", "A drama that delves into the lives of Italian immigrants.", LocalDate.parse("2024-03-08"), 130, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSHstqb2CeFyH3t5MTnWdJYOwhCQTcEyuYjCAxhHAFHu-b7VpAz", "Italian", "Drama", "Alejandro Monteverde", "Christina Dell'Anna, John Lithgow"),
                new Movie("Bob Marley: One Love", "A musical journey through the life of Bob Marley.", LocalDate.parse("2024-02-14"), 135, "https://www.kultunaut.dk/images/film/7104330/plakat.jpg", "English", "Musical/Drama", "Reinaldo Marcus Green", "Kingsley Ben-Adir, Lashana Lynch"),
                new Movie("Love Lies Bleeding", "A tale of romance entangled with crime.", LocalDate.parse("2024-03-08"), 123, "https://upload.wikimedia.org/wikipedia/en/d/dc/Love_lies_bleeding_poster.jpg", "English", "Romance/Crime", "Rose Glass", "Anna Baryshnikov, Kirsten Stewart"),
                new Movie("Godzilla x Kong: The New Empire", "Two ancient titans, Godzilla and Kong, clash in an epic battle as humans unravel their intertwined origins and connection to Skull Island's mysteries.", LocalDate.parse("2024-03-27"), 115, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIpnnMXgQ2DU3l6TsX2cYoFSgL_h0Zwi4L0MfzH3Gly-ZI59jb", "English", "Action", "Adam Wingard", "Rebecca Hall, Brian Tyree Henry, Dan Stevens"),
                new Movie("Ordinary Angels", "A heartwarming drama set in 1994.", LocalDate.parse("2024-12-25"), 117, "https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQS18kbNcDHjpgGHpnTO58Beg8-qKe1xkup3eNfpiQUs8k_1qJA", "English", "Drama", "Jon Gunn", "Hilary Swank, Alan Ritchson"),
                new Movie("One Life", "A gripping war drama that tells a story of survival and bravery.", LocalDate.parse("2024-02-23"), 142, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcTCDF4ZZBsRtrqjFD4iWHKPcKyECTF5iZkGTNnYsovmFqRilPXS", "English", "War/Drama", "James Hawes", "Anthony Hopkins, Lena Olin"),
                new Movie("Knox Goes Away", "A thrilling narrative of a man on the run.", LocalDate.parse("2024-01-01"), 129, "https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcS8wZFC-k4M8jorgEOpssdMUd1D660NVdO_H36n4fk-aKbR6UwY", "English", "Thriller", "Michael Keaton", "Michael Keaton, Al Pacino, Marcia Gay Harden"),
                new Movie("Madame Web", "A sci-fi adventure that explores new dimensions.", LocalDate.parse("2024-02-22"), 134, "https://www.kultunaut.dk/images/film/7104224/plakat.jpg", "English", "Action/Sci-fi", "S.J. Clarkson", "Sydney Sweeney, Dakota Johnson, Isabela Merced"),
                new Movie("Migration", "A comedic look at the adventures of migrating birds.", LocalDate.parse("2023-03-15"), 105, "https://m.media-amazon.com/images/M/MV5BNDY4NTJmNGMtZTU2ZS00MjdkLWIwYTgtNWU5ZWMzZDMwODkyXkEyXkFqcGdeQXVyMTUzMTg2ODkz._V1_FMjpg_UX1000_.jpg", "English", "Adventure/Comedy", "Benjamin Renner", "Kumail Nanjiani, Tesi Gazal"),
                new Movie("Oppenheimer", "A mystery thriller that delves into the mind of J. Robert Oppenheimer.", LocalDate.parse("2023-07-21"), 150, "https://pbs.twimg.com/media/FvUVt3hXgAAxP1H?format=jpg&name=900x900", "English", "Thriller/Mystery", "Christopher Nolan", "Cillian Murphy"),
                new Movie("Snack Shack", "A drama that unfolds in a small town snack shack.", LocalDate.parse("2024-03-15"), 98, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSx1a5IyJoc3nt0LCbW8ph5XtnPYBk_QQfLgI25Y4D_anqOcKoy", "English", "Drama/Comedy", "Adam Rehmeier", "Conor Sherry, Gabrielle LaBelle"),
                new Movie("Poor Things", "The incredible tale about the fantastical evolution of Bella Baxter, a young woman brought back to life by an eccentric but brilliant scientist.", LocalDate.parse("2023-11-02"), 141, "https://www.kultunaut.dk/images/film/7103835/plakat.jpg", "English", "Comedy/Drama/Romance", "Yorgos Lanthimos", "Emma Stone, Mark Ruffalo, Willem Dafoe, Ramy Youssef"),
                new Movie("The Zone of Interest", "Auschwitz commandant Rudolf Höss and his wife Hedwig strive to build a dream life for their family in a house and garden beside the camp.", LocalDate.parse("2023-10-15"), 105, "https://www.kultunaut.dk/images/film/7103949/plakat.jpg", "English", "Drama/History/War", "Jonathan Glazer", "Christian Friedel, Sandra Hüller, Johann Karthaus, Luis Noah Witte"),
                new Movie("Ghostbusters: Frozen Empire", "The Ghostbusters team faces a new threat when a mysterious entity causes a perpetual winter in New York.", LocalDate.parse("2024-06-14"), 135, "https://www.kultunaut.dk/images/film/7104225/plakat.jpg", "English", "Adventure/Comedy/Fantasy", "Jason Reitman", "Paul Rudd, Carrie Coon, Finn Wolfhard, Mckenna Grace"),
                new Movie("The First Omen", "A prequel to the horror classic, exploring the origins of the Omen saga.", LocalDate.parse("2024-04-05"), 110, "https://pbs.twimg.com/media/GC7vYiUX0AEPTCE?format=jpg&name=large", "English", "Horror", "N/A", "Nell Tiger Free, Ralph Ineson, Sonia Braga, Tawfeek Barhom"),
                new Movie("Wicked", "The untold story of the witches of Oz becomes a musical spectacle.", LocalDate.parse("2024-11-27"), 150, "https://upload.wikimedia.org/wikipedia/en/9/90/Wicked_%28film%2C_poster%29.jpeg", "English", "Fantasy/Musical/Romance", "N/A", "Cynthia Erivo, Ariana Grande, Jonathan Bailey, Marissa Bode"),
                new Movie("Nosferatu", "A reimagining of the classic horror tale about a vampire count.", LocalDate.parse("2024-12-25"), 100, "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQhu3PklFL5BMZ_cMeG4pc0BJvBIBq5ZwkVyuPMzP8GDzL_pfCh", "English", "Fantasy/Horror/Mystery", "N/A", "Bill Skarsgård, Willem Dafoe, Aaron Taylor-Johnson, Nicholas Hoult")
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
            for (int dayOffset = 0; dayOffset < 20; dayOffset++) {
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
