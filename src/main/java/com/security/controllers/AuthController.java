package com.security.controllers;

import com.security.dto.LoginUser;
import com.security.entities.User;
import com.security.services.AuthenticationService;
import com.security.services.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginUser user){
        User authUser = this.authenticationService.authenticate(user);
        String jwtToken = jwtService.generateToken(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    }
}
