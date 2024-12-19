package org.onlyup.movie_recommendation_api.dto.rating;

import lombok.Getter;

@Getter
public class RatingResponse {
    private final Long userId;
    private final Long movieId;
    private final int ratingValue;
    private final String comment;

    public RatingResponse(Long userId, Long movieId, int ratingValue, String comment) {
        this.userId = userId;
        this.movieId = movieId;
        this.ratingValue = ratingValue;
        this.comment = comment;
    }

}