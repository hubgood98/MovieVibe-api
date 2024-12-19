package org.onlyup.movie_recommendation_api.service;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.exception.UserAlreadyExistsException;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.onlyup.movie_recommendation_api.repository.RatingRepository;
import org.onlyup.movie_recommendation_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

    @Transactional
    public Rating createOrUpdateRating(Long userId, Long movieId, int ratingValue, String comment) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserAlreadyExistsException("User with ID : " + userId + " Not Found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie Not Found"));

        Rating existRating = ratingRepository.findByUserAndMovie(user, movie);

        double newAverage;
        int voteCount = movie.getVoteCount();
        double totalScore = movie.getVoteAverage() * voteCount;

        //기존에 작성한 평가가 있는경우
        if (existRating != null) {

            int oldRatingValue = existRating.getRatingValue();
            existRating.changeRating(ratingValue, comment);
            totalScore = totalScore - oldRatingValue + ratingValue;
            newAverage = totalScore / voteCount;
        } else {
            existRating = new Rating(user, movie, ratingValue, new Date(), comment);
            movie.voteCountUp();
            voteCount++;
            totalScore += ratingValue;
            newAverage = totalScore / voteCount;
        }

        //평균 반영
        movie.updateVoteAverage(newAverage);
        movieRepository.save(movie);

        return ratingRepository.save(existRating);
    }

    @Transactional(readOnly = true)
    public Page<Rating> findRatingsByMovieId(Long movieId, Pageable pageable) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie Not Found"));
        return ratingRepository.findByMovie(movie, pageable);
    }
}
