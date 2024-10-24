package org.onlyup.movie_recommendation_api.dto.rating;

import lombok.Getter;

@Getter
public class RatingRequest {
    private Long userId;
    private Long movieId;
    private Float ratingValue;

}
