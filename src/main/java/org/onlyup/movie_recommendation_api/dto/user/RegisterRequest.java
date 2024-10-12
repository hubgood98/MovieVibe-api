package org.onlyup.movie_recommendation_api.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    private String accountId;
    private String password;
    private String username;
    private String email;
    private String role;

}
