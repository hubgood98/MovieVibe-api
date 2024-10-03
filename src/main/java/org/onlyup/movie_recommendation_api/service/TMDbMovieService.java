package org.onlyup.movie_recommendation_api.service;

import lombok.RequiredArgsConstructor;
import org.onlyup.movie_recommendation_api.client.TmdbClient;
import org.onlyup.movie_recommendation_api.domain.Movie;
import org.onlyup.movie_recommendation_api.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TMDbMovieService {

    private final TmdbClient tmdbClient;
    private final MovieRepository movieRepository;

    @Value("${movie-recommendation-api.api.key}")
    private String apiKey;

    private final String API_URL = "https://api.themoviedb.org/3/movie/";

    //최근 영화 리스트 가져오기
    public List<Movie> fetchRecentMovies(int page, String language){
        return tmdbClient.fetchNowPlayingMovies(apiKey,page,language).getResults();
    }

    // TMDb에서 가져온 영화 정보를 DB에 저장
    @Transactional
    public void saveMoviesToDB(List<Movie> movies) {
        for (Movie movie : movies) {
            //중복된 영화가 있는지 확인
            if (!movieRepository.findByTitle(movie.getTitle()).isPresent()) {
                movieRepository.save(movie);
            }
        }
    }

    public List<Movie> searchMoviesByTitle(String title){
        return movieRepository.findByTitleContaining(title);
    }

}
