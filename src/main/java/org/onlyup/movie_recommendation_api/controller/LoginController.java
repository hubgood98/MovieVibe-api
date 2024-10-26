package org.onlyup.movie_recommendation_api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {

    @PostMapping("/login")
    public String LoginProcess()
    {
        return "login";
    }
}
