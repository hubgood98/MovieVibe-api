package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.client.TmdbClient;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TMDbMovieService {

    final private TmdbClient tmdbClient;
    final private MovieService movieService;

    @Value("${movie-recommendation-api.api.key")
    private String apiKey;

    private final String API_URL = "https://api.themoviedb.org/3/movie/";

    //최근 영화 정보
    public List<Movie> fetchRecentMovies(int page, String language){
        return tmdbClient.fetchNowPlayingMovies(apiKey,page,language).getResults();
    }

    // 특정 영화 정보 가져오기
    public Movie fetchMovieById(Long movieId){
        return tmdbClient.fetchMovieDetails(movieId,apiKey);
    }

    // TMDb에서 가져온 영화 정보를 DB에 저장
    public Movie saveMovieFromTMDb(Long movieId){
        Movie movie = fetchMovieById(movieId);
        return movieService.saveMovie(movie);
    }

}
