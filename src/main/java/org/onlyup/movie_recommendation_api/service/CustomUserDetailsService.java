package org.onlyup.movie_recommendation_api.service;

import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.dto.CustomUserDetails;
import org.onlyup.movie_recommendation_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userData = userRepository.findByAccountId(username);

        System.out.println(userData.getUsername());
        return new CustomUserDetails(userData);

    }
}
