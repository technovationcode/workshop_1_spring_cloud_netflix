package ro.pentalog.pentabar.reviewservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.env.Environment;

import ro.pentalog.pentabar.reviewservice.repository.MovieReviewDTO;
import ro.pentalog.pentabar.reviewservice.repository.MovieReviewRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/reviews")
public class ReviewController {

    private final MovieReviewRepository movieReviewRepository;
    private final Environment environment;

    public ReviewController(MovieReviewRepository movieReviewRepository, Environment environment) {
        this.movieReviewRepository = movieReviewRepository;
        this.environment = environment;
    }

    @GetMapping("/{movieID}")
    public ResponseEntity<List<MovieReviewDTO>> getReviwsbyMovieID(@PathVariable("movieID") Long movieId) {
        List<MovieReviewDTO> movieReviewsDTO = movieReviewRepository.findAllByMovieId(movieId).stream()
                .map(MovieReviewDTO::new).collect(Collectors.toList());

        String serverPort = environment.getProperty("local.server.port");

        log.info("Here I enter the request, port: {}", serverPort);

        return ResponseEntity.ok(movieReviewsDTO);
    }

}
