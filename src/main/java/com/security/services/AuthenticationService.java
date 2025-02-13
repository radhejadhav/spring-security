package com.security.services;

import com.security.aop.LoginEvent;
import com.security.dao.UserRepository;
import com.security.dto.LoginUser;
import com.security.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository repository) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
    }

    @LoginEvent
    public User authenticate(LoginUser input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return this.repository.findUserByUsername(input.getUsername());
    }
}
