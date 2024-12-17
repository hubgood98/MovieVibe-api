package org.onlyup.movie_recommendation_api.service.rating;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.exception.UserAlreadyExistsException;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.onlyup.movie_recommendation_api.repository.RatingRepository;
import org.onlyup.movie_recommendation_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public Rating createRating(Long userId, Long movieId, int ratingValue, String comment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserAlreadyExistsException("User with ID : " + userId + " Not Found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie Not Found"));

        Rating existRating = ratingRepository.findByUserAndMovie(user, movie);

        if (existRating != null) {
            existRating.changeRating(ratingValue, comment);
        } else {
            existRating = new Rating(user, movie, ratingValue, new Date(), comment);
            movie.voteCountUp(); //투표수 증가
        }

        //레포지트리에서 평균 계산후 반영
        Double newAverage = ratingRepository.averageRatingByMovie(movie);
        movie.updateVoteAverage(newAverage);
        movieRepository.save(movie);

        return ratingRepository.save(existRating);
    }

}
