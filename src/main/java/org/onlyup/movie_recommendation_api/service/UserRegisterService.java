package org.onlyup.movie_recommendation_api.service;

import jakarta.transaction.Transactional;
import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.dto.user.RegisterRequest;
import org.onlyup.movie_recommendation_api.exception.UserAlreadyExistsException;
import org.onlyup.movie_recommendation_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserRegisterService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(RegisterRequest registerRequest) {

        String accountId = registerRequest.getAccountId();

        // ID 중복 체크
        if (userRepository.existsByAccountId(accountId)) {
            throw new UserAlreadyExistsException("이미 사용중인 아이디 입니다.");
        }

        String email = registerRequest.getEmail();
        // 이메일 중복 체크
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("이미 사용중인 이메일 입니다.");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(registerRequest.getPassword());

        User us = new User(
                accountId,
                encodedPassword,
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getRole()
        );

        userRepository.save(us);
    }
}
