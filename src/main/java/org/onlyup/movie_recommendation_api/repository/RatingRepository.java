package org.onlyup.movie_recommendation_api.repository;

import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
}
