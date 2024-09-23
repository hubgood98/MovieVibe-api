package org.onlyup.movie_recommendation_api.service;

import org.onlyup.movie_recommendation_api.domain.User;
import org.onlyup.movie_recommendation_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByAccountId(String accountId){
        return userRepository.findByAccountId(accountId);
    }

    public List<User> getAllUsers(){
        return userRepository.findByisDeletedFalse();
    }

    public void deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(User::markAsDeleted);
    }

    public void deleteByAccountId(String accountId){
        Optional<User> user = userRepository.findByAccountId(accountId);

        user.ifPresent(u -> {
            u.markAsDeleted();
            userRepository.save(u);
        });
    }


}
