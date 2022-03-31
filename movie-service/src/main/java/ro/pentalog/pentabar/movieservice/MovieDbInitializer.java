package ro.pentalog.pentabar.movieservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import ro.pentalog.pentabar.movieservice.repository.Movie;
import ro.pentalog.pentabar.movieservice.repository.MovieRepository;

@Service
public class MovieDbInitializer {

    @Bean
    ApplicationRunner initReviews(MovieRepository movieRepository) {
        return args -> {
            movieRepository.save(Movie.builder()
                    .title("The Lord Of The Rings: The Return of the King")
                    .poster("https://resizing.flixster.com/0HK1Y-onFu90kMEV1KfRbs7-WGE=/206x305/v1.bTsxMTE2NjQyMztqOzE4NDQ0OzEyMDA7ODAwOzEyMDA")
                    .build());

            movieRepository.save(Movie.builder()
                    .title("The Last Samurai")
                    .poster("https://resizing.flixster.com/bJPMRIGxIceRp965aQ6Htekf-xM=/206x305/v1.bTsxMTE2Njg2MTtqOzE4NDQ0OzEyMDA7ODAwOzEyMDA")
                    .build());
        };
    }
}
