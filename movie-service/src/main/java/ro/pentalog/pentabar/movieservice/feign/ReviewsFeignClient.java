package ro.pentalog.pentabar.movieservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ro.pentalog.pentabar.movieservice.feign.hystrix.ReviewServiceFallbackFactory;
import ro.pentalog.pentabar.movieservice.model.MovieReview;

@FeignClient(name = "review-service", /* fallback = ReviewServiceFallback.class, */ fallbackFactory = ReviewServiceFallbackFactory.class)
public interface ReviewsFeignClient {

    @GetMapping("/api/reviews/{movieID}")
    List<MovieReview> getMovieReviews(@PathVariable("movieID") Long movieId);

}