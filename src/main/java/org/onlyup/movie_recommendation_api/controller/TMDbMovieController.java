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

    /**
     * 최근 영화 정보를 가져옵니다.
     *
     * @param page     페이지 번호
     * @param language 언어 코드
     * @return 최근 영화 목록
     */

    @GetMapping("/recent")
    @Operation(summary = "최근 영화 리스트 조회")
    public ResponseEntity<List<Movie>> fetchRecentMovies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "en-US") String language) {
        List<Movie> movies = tmdbMovieService.fetchRecentMovies(page, language);
        return ResponseEntity.ok(movies);
    }

    /**
     * 특정 영화 정보를 가져옵니다.
     *
     * @param movieId 영화 ID
     * @return 영화 정보
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> fetchMovieById(@PathVariable Long movieId) {
        Movie movie = tmdbMovieService.fetchMovieById(movieId);
        return ResponseEntity.ok(movie);
    }

    /**
     * TMDb에서 영화 정보를 가져와 데이터베이스에 저장합니다.
     *
     * @param movieId 영화 ID
     * @return 저장된 영화 정보
     */
    @PostMapping("/save/{movieId}")
    public ResponseEntity<Movie> saveMovieFromTMDb(@PathVariable Long movieId) {
        Movie savedMovie = tmdbMovieService.saveMovieFromTMDb(movieId);
        return ResponseEntity.ok(savedMovie);
    }
}
