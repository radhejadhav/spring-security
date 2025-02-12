package com.security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Data
public class LoginUser {
    private String username;
    private String password;

}
