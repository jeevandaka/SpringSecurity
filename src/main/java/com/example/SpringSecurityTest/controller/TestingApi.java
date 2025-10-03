package com.example.SpringSecurityTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class TestingApi {

    @GetMapping("/public/test")
    public String getCall(){
        return "fake response";
    }

    @GetMapping("/admin/get")
    public String getAdmin() {
        return "fake admin";
    }

    @GetMapping("/jwt/authentication")
    public String getCheck() {
        return "Verified JWT";
    }

}
