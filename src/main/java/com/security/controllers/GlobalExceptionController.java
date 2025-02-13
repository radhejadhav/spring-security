package com.security.controllers;

import com.security.exceptions.RateLimitException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(RateLimitException.class)
    public ResponseEntity<Object> rateLimitException(RateLimitException ex){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(ex.getMessage());
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> globalHandler(Exception exception){
//        return ReqsponseEntity.status(500).body(String.format("\"Error\":\"%1$s\",\n" +
//                        "\"trace\":\"%2$s\"",
//                exception.getMessage(), Arrays.toString(exception.getStackTrace())));
//    }

//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<Object> accessDenied(AccessDeniedException exception){
//        return ResponseEntity.status(403).body(String.format("\"Error\":\"%1$s\",\n" +
//                        "\"trace\":\"%2$s\"",
//                exception.getMessage(), Arrays.toString(exception.getStackTrace())));
//    }
}
