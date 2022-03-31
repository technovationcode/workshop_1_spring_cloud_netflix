package ro.pentalog.pentabar.reviewservice.repository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieReviewDTO {

    public MovieReviewDTO(MovieReview movieReview) {
        this.id = movieReview.getId();
        this.movieId = movieReview.getMovieId();
        this.review = movieReview.getReview();
        this.authorName = movieReview.getAuthorName();
    }

    private Long id;
    private Long movieId;
    private String review;
    private String authorName;
}
