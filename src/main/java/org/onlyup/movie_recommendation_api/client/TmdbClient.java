package org.onlyup.movie_recommendation_api.client;

import org.onlyup.movie_recommendation_api.dto.NowPlayingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdbClient", url = "https://api.themoviedb.org/3/movie")
public interface TmdbClient {

    // 현재 상영 중인 영화 목록 가져오기
    @GetMapping("/now_playing")
    NowPlayingResponse fetchNowPlayingMovies(@RequestParam("api_key") String apiKey,
                                             @RequestParam(value = "page",defaultValue = "1") Integer page,
                                             @RequestParam(value = "language", defaultValue ="en_US") String language);
}
