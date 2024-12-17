package org.onlyup.movie_recommendation_api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "영화 관련 R부분 다루고 있는 컨트롤러")
public class MovieController {
//임시

    @Autowired
    private MovieService movieService;

    @Operation(description = "영화 정보를 필터에 맞게 Page형태로 호출")
    @GetMapping("/list")
    public Page<Movie> searchMovies(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Boolean adult,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false) String voteAverageOrder,
            @RequestParam(required = false) String voteCountOrder) {

        Pageable pageable = PageRequest.of(page, size);
        boolean isAdult = (adult != null) ? adult : false;

        return movieService.searchMovies(title, isAdult, pageable, voteAverageOrder, voteCountOrder);
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<Movie> getMovieDetail(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "영화 정보를 삭제")
    public void deleteMovieById(Long id) {
        movieService.deleteMovieById(id);
    }

}
