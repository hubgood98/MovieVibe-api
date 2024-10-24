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

    RatingService(RatingRepository ratingRepository, MovieRepository movieRepository, UserRepository userRepository){
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public Rating createRating(Long userId, Long movieId, Float rating){
        User user = userRepository.findById(userId).orElseThrow(()->new UserAlreadyExistsException("User with ID : "+userId+" Not Found"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(()->new RuntimeException("Movie Not Found"));

        Rating existRatings = ratingRepository.findByUserAndMovie(user,movie);

        if(existRatings != null){
            existRatings.updateRating(rating);
            return ratingRepository.save(existRatings);
        }else {
            Rating rat = new Rating(user,movie,rating,new Date());
            return ratingRepository.save(rat);
        }

    }

}
