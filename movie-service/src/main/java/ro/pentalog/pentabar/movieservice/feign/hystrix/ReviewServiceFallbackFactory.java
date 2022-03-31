package ro.pentalog.pentabar.movieservice.feign.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ro.pentalog.pentabar.movieservice.feign.ReviewsFeignClient;
import ro.pentalog.pentabar.movieservice.model.MovieReview;

import java.util.Collections;
import java.util.List;

/**
 * Fallback class used for feign client, in case the hystrix circuit breaks
 * This allows access to the underlying exception that broke the circuit
 */
@Component
public class ReviewServiceFallbackFactory implements FallbackFactory<ReviewsFeignClient> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewServiceFallbackFactory.class);

    @Override
    public ReviewsFeignClient create(Throwable throwable) {
        return new ReviewsFeignClient() {
            @Override
            public List<MovieReview> getMovieReviews(Long movieId) {
                LOGGER.error("Error occurred trying to fetch reviews from review service", throwable);
                return Collections.emptyList();
            }
        };
    }
}
