package com.security.controllers;

import com.security.dto.CreateUser;
import com.security.entities.User;
import com.security.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/update")
    public ResponseEntity<User> updateEmail(@RequestParam String email, @RequestParam("user-id") String userId){
        User user = this.userService.updateEmail(email, userId);
        if(user==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("user/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUser user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.userService.createUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity
                .status(HttpStatus.OK).body(
                        this.userService.getAllUser()
                );
    }
}
