package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/ws-chat/**").permitAll()
                .requestMatchers("/chat/message").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()); // disable for websockets + REST ease

        return http.build();
    }
}
