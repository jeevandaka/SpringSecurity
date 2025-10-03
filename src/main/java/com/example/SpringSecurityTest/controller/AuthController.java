package com.example.SpringSecurityTest.controller;

import com.example.SpringSecurityTest.dto.LoginRequestDTO;
import com.example.SpringSecurityTest.dto.LoginResponseDTO;
import com.example.SpringSecurityTest.dto.SignUpRequestDTO;
import com.example.SpringSecurityTest.dto.SignUpResponseDTO;
import com.example.SpringSecurityTest.security.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpRequestDTO sign){
        return ResponseEntity.ok(authService.signUp(sign));
    }
}
