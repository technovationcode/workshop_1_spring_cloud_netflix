package ro.pentalog.pentabar.reviewservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieReviewRepository extends JpaRepository<MovieReview, Long> {

    List<MovieReview> findAllByMovieId(Long movieID);
}
