package com.example.movielib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired UserRepository repo;
    @Autowired PasswordEncoder passwordEncoder;

    public User register(User u) {
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        if (u.getRole() == null) u.setRole("ROLE_USER");
        return repo.save(u);
    }

    public Optional<User> findByUsername(String username){ return repo.findByUsername(username); }
}

