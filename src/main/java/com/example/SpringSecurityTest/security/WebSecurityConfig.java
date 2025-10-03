package com.example.SpringSecurityTest.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth->
                        auth
                                .requestMatchers("/public/**", "/auth/**","/h2-console/**").permitAll()   // give any of your 'get' request endpoint
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().authenticated())  // All other requests require authentication


                .csrf(csrfConfig-> csrfConfig.disable())
                .headers(headers ->
                        headers
                                .frameOptions(frameOptions -> frameOptions.disable()) // ✅ new style
                )
                .sessionManagement( config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();   //when you add build this throws an exception

    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

//    // Custom Authentication Providers
//    @Bean
//    UserDetailsService myInMemoryUserDetailsService(){
//
//        UserDetails user = User  //for user role
//                .withUsername("jeevan")  // Replace <UserName> with the actual username
//                .password(passwordEncoder().encode("jeevan"))   // Replace <UserPassword> with the actual password
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User      //for admin role
//                .withUsername("admin") // Replace <AdminName> with the actual username
//                .password(passwordEncoder().encode("pass"))  // Replace <AdminPassword> with the actual password
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user,admin);
//
//    }
//
//    // Password Encoding
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
