package com.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.SecurityApplicationTests;
import com.security.dto.LoginUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthControllerTest extends SecurityApplicationTests{

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @ParameterizedTest(name = "Test login for password: {0}")
    @DisplayName("Login Test")
    @ValueSource(strings = {"wrong", "123456"})
    public void testLogin(String password) throws Exception {

        LoginUser loginUser = new LoginUser();
        loginUser.setPassword(password);
        loginUser.setUsername("test-user");

        switch (password){
            case "wrong":{
                mvc.perform(post("/auth/login")
                                .content(mapper.writeValueAsString(loginUser))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
            }
            case "123456": {
                MockHttpServletResponse response = mvc.perform(post("/auth/login")
                                .content(mapper.writeValueAsString(loginUser))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse();
                assertInstanceOf(String.class,response.getContentAsString());
//
//                mvc.perform(get("/api/users")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .header(HttpHeaders.AUTHORIZATION, "Bearer "+response.getContentAsString())
//                                .accept(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk());
            }
            default:{

            }
        }
    }
}