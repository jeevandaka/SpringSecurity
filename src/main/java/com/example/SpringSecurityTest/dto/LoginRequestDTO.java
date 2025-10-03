package com.example.SpringSecurityTest.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String userName;
    private String password;
}
