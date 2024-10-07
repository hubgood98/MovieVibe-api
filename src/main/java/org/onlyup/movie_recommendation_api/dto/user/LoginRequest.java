package org.onlyup.movie_recommendation_api.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String accountId;
    private String password;
}
