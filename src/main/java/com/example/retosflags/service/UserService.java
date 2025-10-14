package com.example.retosflags.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.retosflags.model.User;
import com.example.retosflags.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User logUser(User user) {
        Optional<User> found = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return found.orElse(null);
    }
}
