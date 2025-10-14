package com.example.retosflags.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.retosflags.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}


