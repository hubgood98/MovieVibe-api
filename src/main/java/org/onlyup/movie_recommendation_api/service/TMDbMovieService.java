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
    private final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";

    //최근 영화 리스트 가져오기
    public List<Movie> fetchRecentMovies(int page, String language){
        List<Movie> movies = tmdbClient.fetchNowPlayingMovies(apiKey,page,language).getResults();

        for (Movie movie : movies) {
            // Original poster URL
            String originalPosterUrl = IMAGE_BASE_URL + "original" + movie.getPosterPath();
            String w500PosterUrl = IMAGE_BASE_URL + "w500" + movie.getPosterPath();

            movie.setPosterPath(originalPosterUrl);
            movie.setW500PosterPath(w500PosterUrl);
        }

        return movies;
    }

    // TMDb에서 가져온 영화 정보를 DB에 저장
    @Transactional
    public void saveMoviesToDB(List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getPosterPath() == null) {
                System.out.println("Warning: Poster path is null for movie: " + movie.getTitle());
            }
            //중복된 영화가 있는지 확인
            if (!movieRepository.findByTitle(movie.getTitle()).isPresent()) {
                movieRepository.save(movie);
            }
        }
    }


}
