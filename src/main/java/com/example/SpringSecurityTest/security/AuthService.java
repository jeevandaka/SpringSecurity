package com.example.SpringSecurityTest.security;

import com.example.SpringSecurityTest.dto.LoginRequestDTO;
import com.example.SpringSecurityTest.dto.LoginResponseDTO;
import com.example.SpringSecurityTest.dto.SignUpRequestDTO;
import com.example.SpringSecurityTest.dto.SignUpResponseDTO;
import com.example.SpringSecurityTest.entity.User;
import com.example.SpringSecurityTest.repo.UserRepository;
import com.example.SpringSecurityTest.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    final AuthenticationManager authenticationManager;
    final AuthUtils authUtils;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authention = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword())
        );

        User user = (User) authention.getPrincipal();
        String token = authUtils.generateAccessToken(user);

        return LoginResponseDTO.builder()
                .jwt(token)
                .userId(user.getId())
                .build();
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO sign) {
        User user = userRepository.findByUserName(sign.getUserName()).orElse(null);

        if(user != null) throw new IllegalArgumentException("user already exist");

        user = userRepository.save(User.builder()
                        .userName(sign.getUserName())
                        .password(passwordEncoder.encode(sign.getPassword()))
                        .build());

        return SignUpResponseDTO.builder()
                .userName(user.getUsername())
                .id(user.getId())
                .build();
    }
}
