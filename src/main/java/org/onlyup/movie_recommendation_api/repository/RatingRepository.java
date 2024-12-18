package org.onlyup.movie_recommendation_api.repository;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    //기존에 해당영화에 평가했는지 보는 메서드
    Rating findByUserAndMovie(User user, Movie movie);

}
