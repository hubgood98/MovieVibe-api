package org.onlyup.movie_recommendation_api.controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class AdminController {

    @GetMapping("/admin")
    public String adminP(){
        return "어드민 사이트";
    }
}
