package com.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateUser {
    private String fullName;
    private String username;
    private String password;
    private List<String> roles;
}
