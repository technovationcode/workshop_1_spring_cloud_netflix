package ro.pentalog.pentabar.movieservice.feign.hystrix;

import org.springframework.stereotype.Component;
import ro.pentalog.pentabar.movieservice.feign.ReviewsFeignClient;
import ro.pentalog.pentabar.movieservice.model.MovieReview;

import java.util.Collections;
import java.util.List;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 */
@Component
public class ReviewServiceFallback implements ReviewsFeignClient {

    @Override
    public List<MovieReview> getMovieReviews(Long movieId) {
        return Collections.emptyList();
    }
}
