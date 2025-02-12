package com.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.dao.UserRepository;

import com.security.entities.Authority;
import com.security.entities.User;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestConfiguration
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public UserRepository userRepo(){

        User user = User.builder()
                .username("test-user")
                .password("123456")
                .roles(List.of("ADMIN"))
                .id("qweqweqwee")
                .build();

        UserRepository repository = Mockito.mock(UserRepository.class);
        when(repository.findUserByUsername(anyString())).thenReturn(user);

        return repository;
    }
}
