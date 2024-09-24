package org.onlyup.movie_recommendation_api.client;

import org.onlyup.movie_recommendation_api.domain.Movie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdbClient", url = "https://api.themoviedb.org/3/movie")
public interface TmdbClient {

    @GetMapping("/")
    Movie fetchMovieDetails(@PathVariable("movieId") Long movieId,
                            @RequestParam("api_key") String apiKey);
}
