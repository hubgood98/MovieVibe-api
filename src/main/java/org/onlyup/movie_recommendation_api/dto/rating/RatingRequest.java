package org.onlyup.movie_recommendation_api.dto.rating;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingRequest {
    private Long userId;
    private Long movieId;
    private int ratingValue;
    private String comment;

}
