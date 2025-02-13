package com.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitException extends RuntimeException{
    public RateLimitException(String message) {
        super(message);
    }

    public String toApiErrorMessage(final String path) {
        return "Too Many Requests";
//        return new ApiErrorMessage(HttpStatus.TOO_MANY_REQUESTS.value(), HttpStatus.TOO_MANY_REQUESTS.name(), this.getMessage(), path);
    }
}
