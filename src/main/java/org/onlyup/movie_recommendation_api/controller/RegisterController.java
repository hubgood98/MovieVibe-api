package org.onlyup.movie_recommendation_api.controller;

import org.onlyup.movie_recommendation_api.dto.user.RegisterRequest;
import org.onlyup.movie_recommendation_api.service.UserRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class RegisterController {

    private final UserRegisterService userRegisterService;

    RegisterController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }


    @PostMapping("/join")
    public String register(RegisterRequest registerRequest) {

        userRegisterService.registerUser(registerRequest);

        return "Register success!!";
    }
}
