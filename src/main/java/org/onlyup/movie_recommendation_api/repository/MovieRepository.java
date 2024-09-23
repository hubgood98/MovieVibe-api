package org.onlyup.movie_recommendation_api.repository;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
