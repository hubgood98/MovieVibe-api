package org.onlyup.movie_recommendation_api.controller;

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
public class MovieController {
//임시

    @Autowired
    private MovieService movieService;

    @GetMapping("/list")
    public Page<Movie> searchMovies(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Boolean adult,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, size);

        boolean isAdult = (adult != null) ? adult : false;

        return movieService.searchMovies(title, isAdult, pageable);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Movie> getMovieDetail(@PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

}
