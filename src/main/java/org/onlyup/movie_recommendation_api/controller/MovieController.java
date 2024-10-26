package org.onlyup.movie_recommendation_api.controller;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class MovieController {


    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public Page<Movie> searchMovies(
            @RequestParam(required = false, defaultValue = "") String title,
            @RequestParam(required = false) Boolean adult,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page,size);

        boolean isAdult = (adult != null) ? adult : false;

        return movieService.searchMovies(title,isAdult,pageable);
    }

}
