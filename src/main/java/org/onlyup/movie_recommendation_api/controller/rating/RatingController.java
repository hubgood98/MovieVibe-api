package org.onlyup.movie_recommendation_api.controller.rating;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.dto.rating.RatingRequest;
import org.onlyup.movie_recommendation_api.service.rating.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rating")
@Tag(name = "영화 정보 평가데이터 관리")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest) {

        Rating createRating = ratingService.createRating(ratingRequest.getUserId(),ratingRequest.getMovieId(),ratingRequest.getRatingValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(createRating);
    }
}
