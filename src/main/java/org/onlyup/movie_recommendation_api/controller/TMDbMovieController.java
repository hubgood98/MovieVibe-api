package org.onlyup.movie_recommendation_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.service.TMDbMovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@Tag(name = "Tmdb 데이터 가져오는 컨트롤러")
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class TMDbMovieController {

    private final TMDbMovieService tmdbMovieService;

    @GetMapping("/recent")
    @Operation(summary = "최근 영화 리스트 조회")
    public ResponseEntity<List<Movie>> fetchRecentMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "ko") String language) {
        List<Movie> movies = tmdbMovieService.fetchRecentMovies(page, language);
        tmdbMovieService.saveMoviesToDB(movies);

        return ResponseEntity.ok(movies);
    }

}
