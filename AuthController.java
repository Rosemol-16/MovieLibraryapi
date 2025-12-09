package com.example.movielib;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired 
    private UserService userService;

    @Autowired 
    private PasswordEncoder encoder;

    @Autowired 
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User u) {
        return userService.register(u);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest req) {

        var opt = userService.findByUsername(req.getUsername());

        if (opt.isEmpty() || !encoder.matches(req.getPassword(), opt.get().getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(req.getUsername());

        return Map.of("token", token);
    }
}
