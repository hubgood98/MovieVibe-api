package org.onlyup.movie_recommendation_api.controller.rating;

import feign.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.onlyup.movie_recommendation_api.domain.Rating;
import org.onlyup.movie_recommendation_api.dto.rating.RatingRequest;
import org.onlyup.movie_recommendation_api.dto.rating.RatingResponse;
import org.onlyup.movie_recommendation_api.service.RatingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
@Tag(name = "영화 정보 평가데이터 관리")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    @Operation(description = "영화 평가 생성 및 갱신 메서드")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest) {

        Rating createdRating = ratingService.createOrUpdateRating(
                ratingRequest.getUserId(),
                ratingRequest.getMovieId(),
                ratingRequest.getRatingValue(),
                ratingRequest.getComment()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @GetMapping("/{movieId}/ratings")
    public ResponseEntity<Page<RatingResponse>> getRatingsByMovieId(@PathVariable Long movieId, Pageable pageable) {

        Page<Rating> ratings = ratingService.findRatingsByMovieId(movieId, pageable);

        Page<RatingResponse> ratingResponses = ratings.map(rating -> {
            return new RatingResponse(
                    rating.getUser().getUId(),
                    rating.getMovie().getId(),
                    rating.getRatingValue(),
                    rating.getComment());
        });

        return new ResponseEntity<>(ratingResponses,HttpStatus.OK);


    }

}
